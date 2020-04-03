package cn.lhx.rbac.util;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Builder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lee549
 * @date 2020/3/26 10:50
 */

public class PageUtil {

    private static final Integer INITIALCAPCITY = 16;

    // public static <T> Map<String, Object> toMap(IPage<T> page) {
    //
    //     // 数据
    //     List<T> data = page.getRecords();
    //     // 总数
    //     long total = page.getTotal();
    //
    //     return Dict.create().set("data", data).set("total", total);
    // }

    public static <T> Map<String, Object> toMap(IPage<T> page) {
        Map<String, Object> map = new HashMap<>(INITIALCAPCITY);
        map.put("data", page.getRecords());
        map.put("total", page.getTotal());
        return map;

    }

}
