create user v8sys identified by tlv8;
grant connect,resource,dba to v8sys;

create user v8doc identified by tlv8;
grant connect,resource,dba to v8doc;

create user v8oa identified by tlv8;
grant connect,resource,dba to v8oa;

-- Grant/Revoke system privileges 
grant select any table to v8oa;
grant execute any procedure to v8oa;