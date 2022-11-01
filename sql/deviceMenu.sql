-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备信息', '3', '1', '/tt/device', 'C', '0', 'tt:device:view', '#', 'admin', sysdate(), '', null, '设备信息菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备信息查询', @parentId, '1',  '#',  'F', '0', 'tt:device:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备信息新增', @parentId, '2',  '#',  'F', '0', 'tt:device:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备信息修改', @parentId, '3',  '#',  'F', '0', 'tt:device:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备信息删除', @parentId, '4',  '#',  'F', '0', 'tt:device:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备信息导出', @parentId, '5',  '#',  'F', '0', 'tt:device:export',       '#', 'admin', sysdate(), '', null, '');
