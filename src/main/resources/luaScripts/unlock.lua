-- 判断锁的持有者是否是当前线程
if redis.call('get', KEYS[1]) == ARGV[1] then
    -- 如果是，则删除锁并返回 1
    return redis.call('del', KEYS[1])
end
-- 如果不是，则返回 0
return 0
