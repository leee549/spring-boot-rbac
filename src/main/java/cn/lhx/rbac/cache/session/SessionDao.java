package cn.lhx.rbac.cache.session;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author lee549
 * @date 2020/4/10 17:30
 */
@Getter
@Setter
public class SessionDao extends AbstractSessionDAO {

    private RedisTemplate<String, Object> template;

    /**
     * todo：增加
     *        当首次发来请求，会创建新的session 对象，此方法中的参数：session即是
     *        注意：此时的session 由simplesessionFactory创建 且还没有SessionID；
     * @param session
     * @return  session的ID 会随着相应回到浏览器中，存在cookie中
     */
    @Override
    protected Serializable doCreate(Session session) {
        System.out.println("create session");
        //为session指定sessionID默认策略是UUID         生成ID
        Serializable JESSIONID = this.generateSessionId(session);
        //指定ID
        this.assignSessionId(session,JESSIONID);
        //将此session对象存入redis
        //template.opsForValue().set("session:"+JESSIONID,session);
        template.opsForValue().set("session:"+JESSIONID,session,10, TimeUnit.MINUTES);
        return JESSIONID;
    }

    /**
     * todo:查询
     *      通过sessionID，查询对应的session对象
     *
     * @param sessionId 从客户端的cookie传来
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        System.out.println("read session:"+sessionId);
        template.expire("session:"+sessionId,10,TimeUnit.MINUTES);
        return (SimpleSession) template.opsForValue().get("session:"+sessionId);
    }

    /**
     * todo：更新
     *          在用户更改了session中的作用域，需要将用户修改后的session做一个覆盖存储
     * @param session 用户修改作用域后的session 对象（sessionID是不会改变的）
     * @throws UnknownSessionException
     */
    @Override
    public void update(Session session) throws UnknownSessionException {
        System.out.println("update session:"+session.getId());
        template.expire("session:"+session.getId(),10,TimeUnit.MINUTES);
        template.opsForValue().set("session:"+session.getId(),session);
    }

    /**
     * todo：删除
     *          在session过期被发现后，需要删除session
     * @param session 需要删除的session对象
     */
    @Override
    public void delete(Session session) {
        System.out.println("session过期:"+session.getId());
        template.delete("session:"+session.getId());
    }

    /**
     * todo：查询出所有的session，供检测使用
     *
     * @return
     */
    @Override
    public Collection<Session> getActiveSessions() {
        //通过前缀获取session
        Set keys = template.keys("session:*");
        List<Session> sessions = template.opsForValue().multiGet(keys);
        return sessions;
    }
}
