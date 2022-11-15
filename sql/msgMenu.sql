-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息聊天记录', '3', '1', '/tt/msg', 'C', '0', 'tt:msg:view', '#', 'admin', sysdate(), '', null, '消息聊天记录菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息聊天记录查询', @parentId, '1',  '#',  'F', '0', 'tt:msg:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息聊天记录新增', @parentId, '2',  '#',  'F', '0', 'tt:msg:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息聊天记录修改', @parentId, '3',  '#',  'F', '0', 'tt:msg:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息聊天记录删除', @parentId, '4',  '#',  'F', '0', 'tt:msg:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息聊天记录导出', @parentId, '5',  '#',  'F', '0', 'tt:msg:export',       '#', 'admin', sysdate(), '', null, '');
