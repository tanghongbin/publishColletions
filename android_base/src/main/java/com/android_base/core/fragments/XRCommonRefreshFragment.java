package com.android_base.core.fragments;

import me.yokeyword.fragmentation.SupportFragment;

public class XRCommonRefreshFragment extends SupportFragment {
    /***
     * 配置适配器，数据加载模型，
     */
    public static class Configration{
        /**
         * 适配器名称，全命名，需要继承自XRBaseAdapter
         */
        public String adapterClassName;
        public int pageSize; // 每次指定加载的数量
        /**
         *    模型加载名称，全命名 ，必须实现 ‘BaseListDataModule’
         */
        public String moduleClassName;

        public String getAdapterClassName() {
            return adapterClassName;
        }

        public Configration setAdapterClassName(String adapterClassName) {
            this.adapterClassName = adapterClassName;
            return this;
        }

        public String getModuleClassName() {
            return moduleClassName;
        }

        public Configration setModuleClassName(String moduleClassName) {
            this.moduleClassName = moduleClassName;
            return this;
        }
        public Configration setPageSize(int pageSize){
            this.pageSize = pageSize;
            return this;
        }
    }
}