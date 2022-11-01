-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('关注信息', '3', '1', '/tt/notice', 'C', '0', 'tt:notice:view', '#', 'admin', sysdate(), '', null, '关注信息菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('关注信息查询', @parentId, '1',  '#',  'F', '0', 'tt:notice:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('关注信息新增', @parentId, '2',  '#',  'F', '0', 'tt:notice:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('关注信息修改', @parentId, '3',  '#',  'F', '0', 'tt:notice:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('关注信息删除', @parentId, '4',  '#',  'F', '0', 'tt:notice:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('关注信息导出', @parentId, '5',  '#',  'F', '0', 'tt:notice:export',       '#', 'admin', sysdate(), '', null, '');
