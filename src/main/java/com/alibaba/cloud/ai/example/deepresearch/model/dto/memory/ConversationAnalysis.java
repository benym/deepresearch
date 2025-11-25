package com.alibaba.cloud.ai.example.deepresearch.model.dto.memory;

import java.util.Date;

/**
 * @author benym
 */
public class ConversationAnalysis {

    /**
     * 会话ID
     */
    private String conversationId;

    /**
     * 当前置信度分数
     */
    private Integer currentConfidenceScore;

    /**
     * 交互次数
     */
    private Integer interactionCount;

    /**
     * 分析时间
     */
    private Date analysisDate;

}
