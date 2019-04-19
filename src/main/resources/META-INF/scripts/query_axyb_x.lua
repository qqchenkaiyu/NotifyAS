--
-- Created by IntelliJ IDEA.
-- User: HuangYX
-- Date: 2018/6/22
-- Time: 17:24
-- To change this template use File | Settings | File Templates.
--
--local redis = require'redis'
--local cjson = require'cjson'
---------------- inital -----------------------
local resp = {}
local secretNOKey = KEYS[1]
local sopenKey = KEYS[2]

local is_log = ARGV[1] == 'on'
local caller = ARGV[2]
local as_id = ARGV[3]

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
local function addLog(logStr)
    if not logStr then
        return
    end
    local logs = resp['log']
    local logstr
    if logs then
        resp['log'] = logs .. "," .. logStr
    else
        resp['log'] = logStr
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
--    local sopens = redis.call('hmget', sopenKey, 'mode', 'playAnnInfo', 'queryInfo', 'transfermInfo', 'appkey')
--    local mode = sopens[1]
--    if mode then
--        sopen['mode'] = mode
--        sopen['appkey'] = sopens[5]
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
        local state, res =  pcall(function(s) return cjson.decode(s) end, str)
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

local function queryAxBind(asid, bid)
    resp['axBID'] = bid
    addLog('Find axBID ' .. (tostring(bid ~= nil)))
    local axBind = queryBindInfo(asid, bid)
    resp['axBind'] = axBind
    addLog('Find axBind ' .. (tostring(axBind ~= nil)))
    log('axBind')
    log(axBind)
end

---------------- start--------------------------
---------------- secretNO is axyb_ax type-------

log(secretNOKey)
log(caller)
log(as_id)

local secertNOBinds = redis.call('hmget', secretNOKey,
    --- 1
    caller,
    --- 2
    'xyb_' .. caller,
    --- 3
        'axBid')
local axBIDFromCaller = secertNOBinds[1]
local axbBIDFromCaller = secertNOBinds[2]
local axBIDFromAxBid = secertNOBinds[3]

log('axBIDFromCaller')
log(axBIDFromCaller)
log('axbBIDListJsonStr')
log(axbBIDFromCaller)
log('axBIDFromAxBid')
log(axBIDFromAxBid)

if axBIDFromCaller then
    --- 直接通过主叫找到bid，则说明caller是AX绑定的A主叫
    queryAxBind(as_id, axBIDFromCaller)
elseif axbBIDFromCaller then
    --- 通过主叫组合信息找到bid，则说明是B呼到X，组成BXYA呼叫模式

    resp['axbBID'] = axbBIDFromCaller
    addLog('Find axbBID ' .. (tostring(axbBIDFromCaller ~= nil)))
    local axbBind = queryBindInfo(as_id, axbBIDFromCaller)
    resp['axybAxbBind'] = axbBind
    addLog('Find axybAxbBind ' .. (tostring(axbBind ~= nil)))
    log('axybAxbBind')
    log(axbBind)
    if axBIDFromAxBid then
        queryAxBind(as_id, axBIDFromAxBid)
    end


elseif axBIDFromAxBid then
    --- 通过axBid找到bid，则说明是C呼到X，且X还有绑定
    queryAxBind(as_id, axBIDFromAxBid)
else
    local sopen = selectSopenTransfer(sopenKey)
    if sopen then
        resp['sopen'] = sopen
    else
        addLog('SelectSopen is nil')
    end
end

local res = cjson.encode(resp)
log(res)
return res