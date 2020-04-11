package cn.lhx.rbac.cache.session;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

/**
 * @author lee549
 * @date 2020/4/11 11:51
 */
public class MySessionManager extends DefaultWebSessionManager {
    @Override
    public boolean isServletContainerSessions() {
        return true;
    }
}
