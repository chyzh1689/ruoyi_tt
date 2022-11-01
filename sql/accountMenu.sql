-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('账号信息', '3', '1', '/tt/account', 'C', '0', 'tt:account:view', '#', 'admin', sysdate(), '', null, '账号信息菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('账号信息查询', @parentId, '1',  '#',  'F', '0', 'tt:account:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('账号信息新增', @parentId, '2',  '#',  'F', '0', 'tt:account:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('账号信息修改', @parentId, '3',  '#',  'F', '0', 'tt:account:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('账号信息删除', @parentId, '4',  '#',  'F', '0', 'tt:account:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('账号信息导出', @parentId, '5',  '#',  'F', '0', 'tt:account:export',       '#', 'admin', sysdate(), '', null, '');
