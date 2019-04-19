--
-- Created by IntelliJ IDEA.
-- User: witsky
-- Date: 2019/1/14
-- Time: 13:22
-- To change this template use File | Settings | File Templates.
--

---------------- inital -----------------------
local resp = {}
local secretNOKey = KEYS[1]

local as_id = ARGV[1]
local is_log = ARGV[2]
local sopenKey = ARGV[3]
local st = ARGV[4]

---------------- function--------------------------
local function bindKey(asId, bindId)
    local s, e, err = string.find(bindId, '-')
    local lastTwoWord
    if not s then
        lastTwoWord = string.sub(bindId, #bindId - 1, #bindId)
    else
        lastTwoWord = string.sub(bindId, s - 2, s - 1)
    end
    return asId .. ":" .. lastTwoWord
end

local function log(key)
    if is_log then
        redis.log(redis.LOG_WARNING, key)
    end
end

local function selectSopenTransfer(sopenKey)
    local sopen = {}
    local sopens = redis.call('hmget', sopenKey, 'mode', 'playAnnInfo', 'queryInfo', 'transfermInfo', 'appkey', 'requestId', 'smsservice', 'subAppkey')
    local appkey = sopens[5]
    if appkey then
        sopen['requestId'] = sopens[6]
        sopen['appkey'] = appkey
        sopen['subAppkey'] = sopens[8]

        --- 呼叫业务
        local mode = tostring(sopens[1])
        if mode then
            sopen['mode'] = mode
            if mode == '0' then
            elseif mode == '1' then
                sopen['playAnnInfo'] = sopens[2]
            elseif mode == '2' then
                sopen['queryInfo'] = sopens[3]
            elseif mode == '3' then
                sopen['transfermInfo'] = sopens[4]
            else
                --- unsupported mode
            end
        else
        end

        --- 短信业务
        local smsServiceInfo = sopens[7]
        if smsServiceInfo then
            sopen['smsServiceInfo'] = smsServiceInfo
        else
        end

        return sopen
    else
        return nil
    end
end
--local function selectSopenTransfer(sopenKey)
--    local sopen = {}
--    local sopens = redis.call('hmget', sopenKey, 'mode', 'playAnnInfo', 'queryInfo', 'transfermInfo', 'appkey', 'requestId')
--    local mode = tostring(sopens[1])
--    if mode then
--        sopen['mode'] = mode
--        sopen['appkey'] = sopens[5]
--        sopen['requestId'] = sopens[6]
--        if mode == '0' then
--        elseif mode == '1' then
--            sopen['playAnnInfo'] = sopens[2]
--        elseif mode == '2' then
--            sopen['queryInfo'] = sopens[3]
--        elseif mode == '3' then
--            sopen['transfermInfo'] = sopens[4]
--        else
--            --- unsupported mode
--        end
--        return sopen
--    else
--        return nil
--    end
--end

local function json_decode(str)
    log("json_decode")
    log(str)
    if str then
        local state, res = pcall(function(s) return cjson.decode(s) end, str)
        if state then
            return res
        end
        return nil
    else
        return nil
    end
end

local function queryBindInfo(asid, bid)
    local key = bindKey(asid, bid)
    return redis.call('hget', key, bid)
end

--------------- start----

log(secretNOKey)
log(as_id)

local bid
local keys = redis.call('HVALS', secretNOKey)
if keys then
    bid = keys[1]
else
    --- keys is null
end

if bid then
    local s = string.find(bid, '-')
    if not s then
        bid = keys[2]
    else
    end
    --- bind info
    local bind = queryBindInfo(as_id, bid)
    log('bind')
    log(bind)

    if st == 'AXN' then
        resp['axnBind'] = bind
    else
        --- unsupported mode
    end
else
    --- sopen
    local sopen = selectSopenTransfer(sopenKey)
    resp['sopen'] = sopen
end

local res = cjson.encode(resp)
log(res)
return res

