-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('应用参数配置', '3', '1', '/tt/config', 'C', '0', 'tt:config:view', '#', 'admin', sysdate(), '', null, '应用参数配置菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('应用参数配置查询', @parentId, '1',  '#',  'F', '0', 'tt:config:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('应用参数配置新增', @parentId, '2',  '#',  'F', '0', 'tt:config:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('应用参数配置修改', @parentId, '3',  '#',  'F', '0', 'tt:config:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('应用参数配置删除', @parentId, '4',  '#',  'F', '0', 'tt:config:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('应用参数配置导出', @parentId, '5',  '#',  'F', '0', 'tt:config:export',       '#', 'admin', sysdate(), '', null, '');
