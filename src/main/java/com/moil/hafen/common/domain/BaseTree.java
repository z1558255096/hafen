package com.moil.hafen.common.domain;

import java.util.List;

/**
 * @Author 陈子杰
 * @Description 树型结构基本接口
 * @Version 1.0.0
 * @Date 2022/11/25 13:34
 */
public interface BaseTree<T> {
    /**
     * 获取节点id
     *
     * @return 树节点id
     */
    T id();

    /**
     * 获取该节点的父节点id
     *
     * @return 父节点id
     */
    T parentId();

    /**
     * 是否是根节点
     *
     * @return true：根节点
     */
    boolean rootNode();

    /**
     * 设置节点的子节点列表
     *
     * @param children 子节点
     */
    void setChildren(List<? extends BaseTree<T>> children);

    /**
     * 获取所有子节点
     *
     * @return 子节点列表
     */
    List<? extends BaseTree<T>> getChildren();
}
