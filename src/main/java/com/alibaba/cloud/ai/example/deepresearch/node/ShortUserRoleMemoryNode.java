package com.alibaba.cloud.ai.example.deepresearch.node;

import com.alibaba.cloud.ai.example.deepresearch.model.SessionHistory;
import com.alibaba.cloud.ai.example.deepresearch.model.dto.memory.ShortUserRoleExtractResult;
import com.alibaba.cloud.ai.example.deepresearch.service.SessionContextService;
import com.alibaba.cloud.ai.example.deepresearch.util.StateUtil;
import com.alibaba.cloud.ai.example.deepresearch.util.TemplateUtil;
import com.alibaba.cloud.ai.example.deepresearch.util.convert.FluxConverter;
import com.alibaba.cloud.ai.graph.GraphResponse;
import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.NodeAction;
import com.alibaba.cloud.ai.graph.streaming.StreamingOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Short-term user role memory node
 *
 * @author benym
 */
public class ShortUserRoleMemoryNode implements NodeAction {

    private static final Logger logger = LoggerFactory.getLogger(ShortUserRoleMemoryNode.class);

    private final ChatClient shortMemoryAgent;

    private final SessionContextService sessionContextService;

    private final BeanOutputConverter<ShortUserRoleExtractResult> converter;

    public ShortUserRoleMemoryNode(ChatClient shortMemoryAgent, SessionContextService sessionContextService) {
        this.shortMemoryAgent = shortMemoryAgent;
        this.sessionContextService = sessionContextService;
        this.converter = new BeanOutputConverter<>(new ParameterizedTypeReference<>() {
        });
    }

    @Override
    public Map<String, Object> apply(OverAllState state) throws Exception {
        logger.info("short_user_role_memory node is running.");
        List<Message> messages = new ArrayList<>();
        // 1. 获取最近n轮用户提问
        List<SessionHistory> recentReports = sessionContextService.getRecentReports(StateUtil.getSessionId(state), 5);
        StringBuilder historyUserMessages = new StringBuilder();
        for (int i = 0; i < recentReports.size(); i++) {
            String userMessage = String.format("第%s轮, 用户消息:%s\n", i + 1, recentReports.get(i).getUserQuery());
            historyUserMessages.append(userMessage);
        }
        // 2. 添加extract prompt消息
        messages.add(TemplateUtil.getShortMemoryExtractMessage(state, StateUtil.getQuery(state), historyUserMessages.toString()));
        logger.debug("messages: {}", messages);
        Flux<ChatResponse> streamResult = shortMemoryAgent.prompt(converter.getFormat())
                .messages(messages)
                .stream()
                .chatResponse();
        Flux<GraphResponse<StreamingOutput>> generator = FluxConverter.builder()
                .startingNode("short_user_role_extract")
                .startingState(state)
                .mapResult(response -> Map.of("short_user_role_memory",
                        Objects.requireNonNull(response.getResult().getOutput().getText())))
                .buildWithChatResponse(streamResult);
        return Map.of("short_user_role_memory", generator);
    }
}
