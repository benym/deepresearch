package com.alibaba.cloud.ai.example.deepresearch.model.dto.memory;

import com.alibaba.cloud.ai.example.deepresearch.model.enums.ContentDepth;
import com.alibaba.cloud.ai.example.deepresearch.model.enums.DetailLevel;
import com.alibaba.cloud.ai.example.deepresearch.model.enums.ResponseFormat;

/**
 * @author benym
 */
public class CommunicationPreferences {

    /**
     * 详细程度
     */
    private DetailLevel detailLevel;

    /**
     * 内容深度
     */
    private ContentDepth contentDepth;

    /**
     * 响应格式
     */
    private ResponseFormat responseFormat;
}
