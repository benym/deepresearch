package com.alibaba.cloud.ai.example.deepresearch.model.dto.memory;

/**
 * @author benym
 */
public class ShortUserRoleExtractResult {

    /**
     * 会话分析信息
     */
    private ConversationAnalysis conversationAnalysis;

    /**
     * 角色识别信息
     */
    private IdentifiedRole identifiedRole;

    /**
     * 交流偏好信息
     */
    private CommunicationPreferences communicationPreferences;
}
