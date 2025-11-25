package com.alibaba.cloud.ai.example.deepresearch.model.dto.memory;

import com.alibaba.cloud.ai.example.deepresearch.model.enums.ConfidenceLevel;

import java.util.List;

/**
 * @author benym
 */
public class IdentifiedRole {

    /**
     * 角色特征
     */
    private List<String> primaryCharacteristics;

    /**
     * 角色特征证据摘要
     */
    private String evidenceSummary;

    /**
     * 置信度等级
     */
    private ConfidenceLevel confidenceLevel;

    /**
     * 用户特征总览
     */
    private String userOverview;
}
