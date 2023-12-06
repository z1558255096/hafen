package com.moil.hafen.common.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * @Author 陈子杰
 * @Description 基于MybatisPlus的LambdaQueryWrapper扩展
 * 为了解决使用时需要传入的请求参数可能出现的问题：空指针/先判空
 * @Version 1.0.0
 * @Date 2023/8/17 22:39
 */
public class LambdaQueryWrapperX<T> extends LambdaQueryWrapper<T> {
    private static final long serialVersionUID = -5272888928059996864L;
    public LambdaQueryWrapperX<T> likeIfPresent(SFunction<T, ?> column, String val) {
        if (StringUtils.hasText(val)) {
            return (LambdaQueryWrapperX<T>) super.like(column, val);
        }
        return this;
    }

    public LambdaQueryWrapperX<T> likeOrIfPresent(String val, SFunction<T, ?>... columnArray) {
        if (StringUtils.hasText(val)) {
            return (LambdaQueryWrapperX<T>) super.and(i -> i.like(columnArray[0], val).or().like(columnArray[1], val));
        }
        return this;
    }

    public LambdaQueryWrapperX<T> likeTwoOrIfPresent(SFunction<T, ?> column1, SFunction<T, ?> column2, String val) {
        if (StringUtils.hasText(val)) {
            return (LambdaQueryWrapperX<T>) super.and(i -> i.like(column1, val).or().like(column2, val));
        }
        return this;
    }

    public LambdaQueryWrapperX<T> likeTreeOrIfPresent(SFunction<T, ?> column1, SFunction<T, ?> column2, SFunction<T, ?> column3, String val) {
        if (StringUtils.hasText(val)) {
            return (LambdaQueryWrapperX<T>) super.and(i -> i.like(column1, val).or().like(column2, val).or().like(column3, val));
        }
        return this;
    }

    public LambdaQueryWrapperX<T> inIfPresent(SFunction<T, ?> column, Collection<?> values) {
        if (!CollectionUtils.isEmpty(values)) {
            return (LambdaQueryWrapperX<T>) super.in(column, values);
        }
        return this;
    }

    public LambdaQueryWrapperX<T> inIfPresent(SFunction<T, ?> column, Object... values) {
        if (!ArrayUtils.isEmpty(values)) {
            return (LambdaQueryWrapperX<T>) super.in(column, values);
        }
        return this;
    }

    public LambdaQueryWrapperX<T> notInIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (LambdaQueryWrapperX<T>) super.notIn(column, val);
        }
        return this;
    }

    public LambdaQueryWrapperX<T> notInIfPresent(SFunction<T, ?> column, Collection<?> val) {
        if (val != null) {
            return (LambdaQueryWrapperX<T>) super.notIn(column, val);
        }
        return this;
    }

    public LambdaQueryWrapperX<T> eqIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            // 值为String
            if (val instanceof String) {
                if (!val.toString().trim().equals("")) {
                    return (LambdaQueryWrapperX<T>) super.eq(column, val);
                }
            } else {
                return (LambdaQueryWrapperX<T>) super.eq(column, val);
            }
        }
        return this;
    }

    public LambdaQueryWrapperX<T> twoValEqIfPresent(SFunction<T, ?> column1, Object val, Object val2) {
        if (val != null) {
            return (LambdaQueryWrapperX<T>) super.and(i -> i.eq(column1, val).or().eq(column1, val2));
        }
        return this;
    }

    public LambdaQueryWrapperX<T> neIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (LambdaQueryWrapperX<T>) super.ne(column, val);
        }
        return this;
    }

    public LambdaQueryWrapperX<T> gtIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (LambdaQueryWrapperX<T>) super.gt(column, val);
        }
        return this;
    }

    public LambdaQueryWrapperX<T> geIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (LambdaQueryWrapperX<T>) super.ge(column, val);
        }
        return this;
    }

    public LambdaQueryWrapperX<T> ltIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (LambdaQueryWrapperX<T>) super.lt(column, val);
        }
        return this;
    }

    public LambdaQueryWrapperX<T> leIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (LambdaQueryWrapperX<T>) super.le(column, val);
        }
        return this;
    }

    public LambdaQueryWrapperX<T> betweenIfPresent(SFunction<T, ?> column, Object val1, Object val2) {
        if (val1 != null && val2 != null) {
            return (LambdaQueryWrapperX<T>) super.between(column, val1, val2);
        }
        if (val1 != null) {
            return (LambdaQueryWrapperX<T>) ge(column, val1);
        }
        if (val2 != null) {
            return (LambdaQueryWrapperX<T>) le(column, val2);
        }
        return this;
    }

    @Override
    public LambdaQueryWrapperX<T> between(SFunction<T, ?> column, Object val1, Object val2) {
        return (LambdaQueryWrapperX<T>) super.between(column, val1, val2);
    }

    @Override
    public LambdaQueryWrapperX<T> eq(boolean condition, SFunction<T, ?> column, Object val) {
        super.eq(condition, column, val);
        return this;
    }

    @Override
    public LambdaQueryWrapperX<T> eq(SFunction<T, ?> column, Object val) {
        super.eq(column, val);
        return this;
    }

    @Override
    public LambdaQueryWrapperX<T> orderByDesc(SFunction<T, ?> column) {
        super.orderByDesc(true, column);
        return this;
    }

    @Override
    public LambdaQueryWrapperX<T> last(String lastSql) {
        super.last(lastSql);
        return this;
    }

    @Override
    public LambdaQueryWrapperX<T> in(SFunction<T, ?> column, Collection<?> coll) {
        super.in(column, coll);
        return this;
    }
}
