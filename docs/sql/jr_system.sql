use jr_system;

create table gen_config
(
    id             bigint auto_increment
        primary key,
    table_name     varchar(100)     not null comment '表名',
    module_name    varchar(100)     null comment '模块名',
    package_name   varchar(255)     not null comment '包名',
    business_name  varchar(100)     not null comment '业务名',
    entity_name    varchar(100)     not null comment '实体类名',
    author         varchar(50)      not null comment '作者',
    parent_menu_id bigint           null comment '上级菜单ID，对应sys_menu的id ',
    create_time    datetime         null comment '创建时间',
    update_time    datetime         null comment '更新时间',
    is_deleted     bit default b'0' null comment '是否删除',
    constraint uk_tablename
        unique (table_name)
)
    comment '代码生成基础配置表';

create table gen_field_config
(
    id               bigint auto_increment
        primary key,
    config_id        bigint               not null comment '关联的配置ID',
    column_name      varchar(100)         null,
    column_type      varchar(50)          null,
    column_length    int                  null,
    field_name       varchar(100)         not null comment '字段名称',
    field_type       varchar(100)         null comment '字段类型',
    field_sort       int                  null comment '字段排序',
    field_comment    varchar(255)         null comment '字段描述',
    max_length       int                  null,
    is_required      tinyint(1)           null comment '是否必填',
    is_show_in_list  tinyint(1) default 0 null comment '是否在列表显示',
    is_show_in_form  tinyint(1) default 0 null comment '是否在表单显示',
    is_show_in_query tinyint(1) default 0 null comment '是否在查询条件显示',
    query_type       tinyint              null comment '查询方式',
    form_type        tinyint              null comment '表单类型',
    dict_type        varchar(50)          null comment '字典类型',
    create_time      datetime             null comment '创建时间',
    update_time      datetime             null comment '更新时间'
)
    comment '代码生成字段配置表';

create index config_id
    on gen_field_config (config_id);

create table sys_config
(
    id           bigint auto_increment
        primary key,
    config_name  varchar(50)       not null comment '配置名称',
    config_key   varchar(50)       not null comment '配置key',
    config_value varchar(100)      not null comment '配置值',
    remark       varchar(255)      null comment '备注',
    create_time  datetime          null comment '创建时间',
    create_by    bigint            null comment '创建人ID',
    update_time  datetime          null comment '更新时间',
    update_by    bigint            null comment '更新人ID',
    is_deleted   tinyint default 0 not null comment '逻辑删除标识(0-未删除 1-已删除)'
)
    comment '系统配置表';

create table sys_dept
(
    id          bigint auto_increment comment '主键'
        primary key,
    name        varchar(100)       not null comment '部门名称',
    code        varchar(100)       not null comment '部门编号',
    parent_id   bigint   default 0 null comment '父节点id',
    tree_path   varchar(255)       not null comment '父节点id路径',
    sort        smallint default 0 null comment '显示顺序',
    status      tinyint  default 1 null comment '状态(1-正常 0-禁用)',
    create_by   bigint             null comment '创建人ID',
    create_time datetime           null comment '创建时间',
    update_by   bigint             null comment '修改人ID',
    update_time datetime           null comment '更新时间',
    is_deleted  tinyint  default 0 null comment '逻辑删除标识(1-已删除 0-未删除)',
    constraint uk_code
        unique (code) comment '部门编号唯一索引'
)
    comment '部门表';

create table sys_dict
(
    id          bigint auto_increment comment '主键 '
        primary key,
    dict_code   varchar(50)          null comment '类型编码',
    name        varchar(50)          null comment '类型名称',
    status      tinyint(1) default 0 null comment '状态(0:正常;1:禁用)',
    remark      varchar(255)         null comment '备注',
    create_time datetime             null comment '创建时间',
    create_by   bigint               null comment '创建人ID',
    update_time datetime             null comment '更新时间',
    update_by   bigint               null comment '修改人ID',
    is_deleted  tinyint    default 0 null comment '是否删除(1-删除，0-未删除)'
)
    comment '字典表';

create index idx_dict_code
    on sys_dict (dict_code);

create table sys_dict_item
(
    id          bigint auto_increment comment '主键'
        primary key,
    dict_code   varchar(50)       null comment '关联字典编码，与sys_dict表中的dict_code对应',
    value       varchar(50)       null comment '字典项值',
    label       varchar(100)      null comment '字典项标签',
    tag_type    varchar(50)       null comment '标签类型，用于前端样式展示（如success、warning等）',
    status      tinyint default 0 null comment '状态（1-正常，0-禁用）',
    sort        int     default 0 null comment '排序',
    remark      varchar(255)      null comment '备注',
    create_time datetime          null comment '创建时间',
    create_by   bigint            null comment '创建人ID',
    update_time datetime          null comment '更新时间',
    update_by   bigint            null comment '修改人ID'
)
    comment '字典项表';

create table sys_log
(
    id               bigint auto_increment comment '主键'
        primary key,
    module           varchar(50)       not null comment '日志模块',
    request_method   varchar(64)       not null comment '请求方式',
    request_params   mediumtext        null comment '请求参数(批量请求参数可能会超过text)',
    response_content mediumtext        null comment '返回参数',
    content          varchar(255)      not null comment '日志内容',
    request_uri      varchar(255)      null comment '请求路径',
    method           varchar(255)      null comment '方法名',
    ip               varchar(45)       null comment 'IP地址',
    province         varchar(100)      null comment '省份',
    city             varchar(100)      null comment '城市',
    execution_time   bigint            null comment '执行时间(ms)',
    browser          varchar(100)      null comment '浏览器',
    browser_version  varchar(100)      null comment '浏览器版本',
    os               varchar(100)      null comment '终端系统',
    create_by        bigint            null comment '创建人ID',
    create_time      datetime          null comment '创建时间',
    is_deleted       tinyint default 0 null comment '逻辑删除标识(1-已删除 0-未删除)'
)
    comment '系统日志表' engine = MyISAM;

create index idx_create_time
    on sys_log (create_time);

create table sys_menu
(
    id          bigint auto_increment comment 'ID'
        primary key,
    parent_id   bigint               not null comment '父菜单ID',
    tree_path   varchar(255)         null comment '父节点ID路径',
    name        varchar(64)          not null comment '菜单名称',
    type        tinyint              not null comment '菜单类型（1-菜单 2-目录 3-外链 4-按钮）',
    route_name  varchar(255)         null comment '路由名称（Vue Router 中用于命名路由）',
    route_path  varchar(128)         null comment '路由路径（Vue Router 中定义的 URL 路径）',
    component   varchar(128)         null comment '组件路径（组件页面完整路径，相对于 src/views/，缺省后缀 .vue）',
    perm        varchar(128)         null comment '【按钮】权限标识',
    always_show tinyint    default 0 null comment '【目录】只有一个子路由是否始终显示（1-是 0-否）',
    keep_alive  tinyint    default 0 null comment '【菜单】是否开启页面缓存（1-是 0-否）',
    visible     tinyint(1) default 1 null comment '显示状态（1-显示 0-隐藏）',
    sort        int        default 0 null comment '排序',
    icon        varchar(64)          null comment '菜单图标',
    redirect    varchar(128)         null comment '跳转路径',
    create_time datetime             null comment '创建时间',
    update_time datetime             null comment '更新时间',
    params      varchar(255)         null comment '路由参数'
)
    comment '菜单管理';

create table sys_notice
(
    id              bigint auto_increment
        primary key,
    title           varchar(50)          null comment '通知标题',
    content         mediumtext           null comment '通知内容',
    type            tinyint              not null comment '通知类型（关联字典编码：notice_type）',
    level           varchar(5)           not null comment '通知等级（字典code：notice_level）',
    target_type     tinyint              not null comment '目标类型（1: 全体, 2: 指定）',
    target_user_ids varchar(255)         null comment '目标人ID集合（多个使用英文逗号,分割）',
    publisher_id    bigint               null comment '发布人ID',
    publish_status  tinyint    default 0 null comment '发布状态（0: 未发布, 1: 已发布, -1: 已撤回）',
    publish_time    datetime             null comment '发布时间',
    revoke_time     datetime             null comment '撤回时间',
    create_by       bigint               not null comment '创建人ID',
    create_time     datetime             not null comment '创建时间',
    update_by       bigint               null comment '更新人ID',
    update_time     datetime             null comment '更新时间',
    is_deleted      tinyint(1) default 0 null comment '是否删除（0: 未删除, 1: 已删除）'
)
    comment '通知公告表';

create table sys_role
(
    id          bigint auto_increment
        primary key,
    name        varchar(64)          not null comment '角色名称',
    code        varchar(32)          not null comment '角色编码',
    sort        int                  null comment '显示顺序',
    status      tinyint(1) default 1 null comment '角色状态(1-正常 0-停用)',
    data_scope  tinyint              null comment '数据权限(1-所有数据 2-部门及子部门数据 3-本部门数据 4-本人数据)',
    create_by   bigint               null comment '创建人 ID',
    create_time datetime             null comment '创建时间',
    update_by   bigint               null comment '更新人ID',
    update_time datetime             null comment '更新时间',
    is_deleted  tinyint(1) default 0 null comment '逻辑删除标识(0-未删除 1-已删除)',
    constraint uk_code
        unique (code) comment '角色编码唯一索引',
    constraint uk_name
        unique (name) comment '角色名称唯一索引'
)
    comment '角色表';

create table sys_role_menu
(
    id      bigint auto_increment comment 'ID'
        primary key,
    role_id bigint not null comment '角色ID',
    menu_id bigint not null comment '菜单ID',
    constraint uk_roleid_menuid
        unique (role_id, menu_id) comment '角色菜单唯一索引'
)
    comment '角色和菜单关联表';

create table sys_user
(
    id          bigint auto_increment
        primary key,
    username    varchar(64)          null comment '用户名',
    nickname    varchar(64)          null comment '昵称',
    gender      tinyint(1) default 1 null comment '性别((1-男 2-女 0-保密)',
    password    varchar(100)         null comment '密码',
    dept_id     int                  null comment '部门ID',
    avatar      varchar(255)         null comment '用户头像',
    mobile      varchar(20)          null comment '联系方式',
    status      tinyint(1) default 1 null comment '状态(1-正常 0-禁用)',
    email       varchar(128)         null comment '用户邮箱',
    create_time datetime             null comment '创建时间',
    create_by   bigint               null comment '创建人ID',
    update_time datetime             null comment '更新时间',
    update_by   bigint               null comment '修改人ID',
    is_deleted  tinyint(1) default 0 null comment '逻辑删除标识(0-未删除 1-已删除)',
    openid      char(28)             null comment '微信 openid',
    constraint login_name
        unique (username)
)
    comment '用户信息表';

create table sys_user_notice
(
    id          bigint auto_increment comment 'id'
        primary key,
    notice_id   bigint            not null comment '公共通知id',
    user_id     bigint            not null comment '用户id',
    is_read     bigint  default 0 null comment '读取状态（0: 未读, 1: 已读）',
    read_time   datetime          null comment '阅读时间',
    create_time datetime          not null comment '创建时间',
    update_time datetime          null comment '更新时间',
    is_deleted  tinyint default 0 null comment '逻辑删除(0: 未删除, 1: 已删除)'
)
    comment '用户通知公告表';

create table sys_user_role
(
    id      bigint auto_increment comment 'ID'
        primary key,
    user_id bigint not null comment '用户ID',
    role_id bigint not null comment '角色ID',
    constraint sys_user_role_upk
        unique (user_id, role_id)
)
    comment '用户和角色关联表';

create table undo_log
(
    id            bigint auto_increment
        primary key,
    branch_id     bigint       not null,
    xid           varchar(100) not null,
    context       varchar(128) not null,
    rollback_info longblob     not null,
    log_status    int          not null,
    log_created   datetime     not null,
    log_modified  datetime     not null,
    ext           varchar(100) null,
    constraint ux_undo_log
        unique (xid, branch_id)
)
    charset = utf8;


INSERT INTO sys_config (id, config_name, config_key, config_value, remark, create_time, create_by, update_time, update_by, is_deleted) VALUES (1, '系统限流QPS', 'IP_QPS_THRESHOLD_LIMIT', '10', '单个IP请求的最大每秒查询数（QPS）阈值Key', '2025-06-13 11:42:38', 1, null, null, 0);

INSERT INTO sys_dept (id, name, code, parent_id, tree_path, sort, status, create_by, create_time, update_by, update_time, is_deleted) VALUES (1, '有来技术', 'YOULAI', 0, '0', 1, 1, 1, null, 1, '2025-06-13 11:42:37', 0);
INSERT INTO sys_dept (id, name, code, parent_id, tree_path, sort, status, create_by, create_time, update_by, update_time, is_deleted) VALUES (2, '研发部门', 'RD001', 1, '0,1', 1, 1, 2, null, 2, '2025-06-13 11:42:37', 0);
INSERT INTO sys_dept (id, name, code, parent_id, tree_path, sort, status, create_by, create_time, update_by, update_time, is_deleted) VALUES (3, '测试部门', 'QA001', 1, '0,1', 1, 1, 2, null, 2, '2025-06-13 11:42:37', 0);

INSERT INTO sys_dict (id, dict_code, name, status, remark, create_time, create_by, update_time, update_by, is_deleted) VALUES (1, 'gender', '性别', 1, null, '2025-06-13 11:42:37', 1, '2025-06-13 11:42:37', 1, 0);
INSERT INTO sys_dict (id, dict_code, name, status, remark, create_time, create_by, update_time, update_by, is_deleted) VALUES (2, 'notice_type', '通知类型', 1, null, '2025-06-13 11:42:37', 1, '2025-06-13 11:42:37', 1, 0);
INSERT INTO sys_dict (id, dict_code, name, status, remark, create_time, create_by, update_time, update_by, is_deleted) VALUES (3, 'notice_level', '通知级别', 1, null, '2025-06-13 11:42:37', 1, '2025-06-13 11:42:37', 1, 0);

INSERT INTO sys_dict_item (id, dict_code, value, label, tag_type, status, sort, remark, create_time, create_by, update_time, update_by) VALUES (1, 'gender', '1', '男', 'primary', 1, 1, null, '2025-06-13 11:42:37', 1, '2025-06-13 11:42:37', 1);
INSERT INTO sys_dict_item (id, dict_code, value, label, tag_type, status, sort, remark, create_time, create_by, update_time, update_by) VALUES (2, 'gender', '2', '女', 'danger', 1, 2, null, '2025-06-13 11:42:37', 1, '2025-06-13 11:42:37', 1);
INSERT INTO sys_dict_item (id, dict_code, value, label, tag_type, status, sort, remark, create_time, create_by, update_time, update_by) VALUES (3, 'gender', '0', '保密', 'info', 1, 3, null, '2025-06-13 11:42:37', 1, '2025-06-13 11:42:37', 1);
INSERT INTO sys_dict_item (id, dict_code, value, label, tag_type, status, sort, remark, create_time, create_by, update_time, update_by) VALUES (4, 'notice_type', '1', '系统升级', 'success', 1, 1, '', '2025-06-13 11:42:37', 1, '2025-06-13 11:42:37', 1);
INSERT INTO sys_dict_item (id, dict_code, value, label, tag_type, status, sort, remark, create_time, create_by, update_time, update_by) VALUES (5, 'notice_type', '2', '系统维护', 'primary', 1, 2, '', '2025-06-13 11:42:37', 1, '2025-06-13 11:42:37', 1);
INSERT INTO sys_dict_item (id, dict_code, value, label, tag_type, status, sort, remark, create_time, create_by, update_time, update_by) VALUES (6, 'notice_type', '3', '安全警告', 'danger', 1, 3, '', '2025-06-13 11:42:37', 1, '2025-06-13 11:42:37', 1);
INSERT INTO sys_dict_item (id, dict_code, value, label, tag_type, status, sort, remark, create_time, create_by, update_time, update_by) VALUES (7, 'notice_type', '4', '假期通知', 'success', 1, 4, '', '2025-06-13 11:42:37', 1, '2025-06-13 11:42:37', 1);
INSERT INTO sys_dict_item (id, dict_code, value, label, tag_type, status, sort, remark, create_time, create_by, update_time, update_by) VALUES (8, 'notice_type', '5', '公司新闻', 'primary', 1, 5, '', '2025-06-13 11:42:37', 1, '2025-06-13 11:42:37', 1);
INSERT INTO sys_dict_item (id, dict_code, value, label, tag_type, status, sort, remark, create_time, create_by, update_time, update_by) VALUES (9, 'notice_type', '99', '其他', 'info', 1, 99, '', '2025-06-13 11:42:37', 1, '2025-06-13 11:42:37', 1);
INSERT INTO sys_dict_item (id, dict_code, value, label, tag_type, status, sort, remark, create_time, create_by, update_time, update_by) VALUES (10, 'notice_level', 'L', '低', 'info', 1, 1, '', '2025-06-13 11:42:37', 1, '2025-06-13 11:42:37', 1);
INSERT INTO sys_dict_item (id, dict_code, value, label, tag_type, status, sort, remark, create_time, create_by, update_time, update_by) VALUES (11, 'notice_level', 'M', '中', 'warning', 1, 2, '', '2025-06-13 11:42:37', 1, '2025-06-13 11:42:37', 1);
INSERT INTO sys_dict_item (id, dict_code, value, label, tag_type, status, sort, remark, create_time, create_by, update_time, update_by) VALUES (12, 'notice_level', 'H', '高', 'danger', 1, 3, '', '2025-06-13 11:42:37', 1, '2025-06-13 11:42:37', 1);

INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (1, 0, '0', '系统管理', 2, '', '/system', 'Layout', null, null, null, 1, 1, 'system', '/system/user', '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (2, 1, '0,1', '用户管理', 1, 'User', 'user', 'system/user/index', null, null, 1, 1, 1, 'el-icon-User', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (3, 1, '0,1', '角色管理', 1, 'Role', 'role', 'system/role/index', null, null, 1, 1, 2, 'role', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (4, 1, '0,1', '菜单管理', 1, 'SysMenu', 'menu', 'system/menu/index', null, null, 1, 1, 3, 'menu', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (5, 1, '0,1', '部门管理', 1, 'Dept', 'dept', 'system/dept/index', null, null, 1, 1, 4, 'tree', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (6, 1, '0,1', '字典管理', 1, 'Dict', 'dict', 'system/dict/index', null, null, 1, 1, 5, 'dict', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (9, 0, '0', '营销管理', 2, null, '/sms', 'Layout', null, 1, null, 1, 5, 'el-icon-TrendCharts', null, '2021-08-28 09:12:21', '2025-07-11 15:58:18', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (10, 9, '0,9', '广告列表', 1, 'Advert', 'advert', 'sms/advert/index', null, null, 1, 1, 1, 'bell', null, '2021-08-28 09:12:21', '2025-07-11 15:06:29', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (11, 0, '0', '商品管理', 1, null, '/pms', 'Layout', null, null, 1, 1, 2, 'el-icon-Goods', '/pms/goods', '2021-08-28 09:12:21', '2024-03-03 23:54:35', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (12, 11, '0,11', '商品列表', 1, 'Goods', 'goods', 'pms/goods/index', null, null, 1, 1, 1, 'el-icon-GoodsFilled', null, '2021-08-28 09:12:21', '2025-07-11 15:07:47', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (13, 0, '0', '订单管理', 2, null, '/oms', 'Layout', null, 1, 1, 1, 3, 'el-icon-ShoppingCart', '/oms/order', '2021-08-28 09:12:21', '2024-03-03 23:52:32', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (14, 13, '13', '订单列表', 1, null, 'order', 'oms/order/index', null, null, 1, 1, 1, 'el-icon-Document', null, '2021-08-28 09:12:21', '2024-03-03 23:45:09', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (15, 0, '0', '会员管理', 2, null, '/ums', 'Layout', null, 1, 1, 1, 4, 'user', '/ums/member', '2021-08-28 09:12:21', '2025-07-11 15:58:00', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (16, 15, '0,15', '会员列表', 1, 'Member', 'member', 'ums/member/index', null, null, 1, 1, 1, 'el-icon-Document', null, '2021-08-28 09:12:21', '2025-07-11 15:04:45', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (17, 11, '0,11', '品牌管理', 1, 'Brand', 'brand', 'pms/brand/index', null, null, 1, 1, 5, 'el-icon-Coordinate', null, '2021-08-28 09:12:21', '2025-07-11 15:09:54', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (18, 11, '0,11', '商品分类', 1, null, 'category', 'pms/category/index', null, null, 1, 1, 3, 'menu', null, '2021-08-28 09:12:21', '2021-08-28 09:12:21', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (19, 11, '0,11', '商品上架', 1, 'GoodsDetail', 'goods-detail', 'pms/goods/detail', null, null, null, 1, 2, 'up', null, '2021-08-28 09:12:21', '2025-07-11 15:08:33', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (20, 0, '0', '多级菜单', 2, null, '/multi-level', 'Layout', null, 1, null, 1, 9, 'cascader', '', '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (21, 20, '0,20', '菜单一级', 1, null, 'multi-level1', 'demo/multi-level/level1', null, 1, null, 1, 1, '', '', '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (22, 21, '0,20,21', '菜单二级', 1, null, 'multi-level2', 'demo/multi-level/children/level2', null, 0, null, 1, 1, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (23, 22, '0,20,21,22', '菜单三级-1', 1, null, 'multi-level3-1', 'demo/multi-level/children/children/level3-1', null, 0, 1, 1, 1, '', '', '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (24, 22, '0,20,21,22', '菜单三级-2', 1, null, 'multi-level3-2', 'demo/multi-level/children/children/level3-2', null, 0, 1, 1, 2, '', '', '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (26, 0, '0', '平台文档', 2, '', '/doc', 'Layout', null, null, null, 1, 8, 'document', 'https://juejin.cn/post/7228990409909108793', '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (30, 26, '0,26', '平台文档(外链)', 3, null, 'https://juejin.cn/post/7228990409909108793', '', null, null, null, 1, 2, 'document', '', '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (31, 2, '0,1,2', '用户新增', 4, null, '', null, 'sys:user:add', null, null, 1, 1, '', '', '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (32, 2, '0,1,2', '用户编辑', 4, null, '', null, 'sys:user:edit', null, null, 1, 2, '', '', '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (33, 2, '0,1,2', '用户删除', 4, null, '', null, 'sys:user:delete', null, null, 1, 3, '', '', '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (36, 0, '0', '组件封装', 2, null, '/component', 'Layout', null, null, null, 1, 10, 'menu', '', '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (37, 36, '0,36', '富文本编辑器', 1, null, 'wang-editor', 'demo/wang-editor', null, null, 1, 1, 2, '', '', null, null, null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (38, 36, '0,36', '图片上传', 1, null, 'upload', 'demo/upload', null, null, 1, 1, 3, '', '', '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (39, 36, '0,36', '图标选择器', 1, null, 'icon-selector', 'demo/icon-selector', null, null, 1, 1, 4, '', '', '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (40, 0, '0', '接口文档', 2, null, '/api', 'Layout', null, 1, null, 1, 7, 'api', '', '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (41, 40, '0,40', 'Apifox', 1, null, 'apifox', 'demo/api/apifox', null, null, 1, 1, 1, 'api', '', '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (70, 3, '0,1,3', '角色新增', 4, null, '', null, 'sys:role:add', null, null, 1, 2, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (71, 3, '0,1,3', '角色编辑', 4, null, '', null, 'sys:role:edit', null, null, 1, 3, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (72, 3, '0,1,3', '角色删除', 4, null, '', null, 'sys:role:delete', null, null, 1, 4, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (73, 4, '0,1,4', '菜单新增', 4, null, '', null, 'sys:menu:add', null, null, 1, 1, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (74, 4, '0,1,4', '菜单编辑', 4, null, '', null, 'sys:menu:edit', null, null, 1, 3, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (75, 4, '0,1,4', '菜单删除', 4, null, '', null, 'sys:menu:delete', null, null, 1, 3, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (76, 5, '0,1,5', '部门新增', 4, null, '', null, 'sys:dept:add', null, null, 1, 1, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (77, 5, '0,1,5', '部门编辑', 4, null, '', null, 'sys:dept:edit', null, null, 1, 2, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (78, 5, '0,1,5', '部门删除', 4, null, '', null, 'sys:dept:delete', null, null, 1, 3, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (79, 6, '0,1,6', '字典新增', 4, null, '', null, 'sys:dict:add', null, null, 1, 1, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (81, 6, '0,1,6', '字典编辑', 4, null, '', null, 'sys:dict:edit', null, null, 1, 2, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (84, 6, '0,1,6', '字典删除', 4, null, '', null, 'sys:dict:delete', null, null, 1, 3, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (88, 2, '0,1,2', '重置密码', 4, null, '', null, 'sys:user:reset-password', null, null, 1, 4, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (89, 0, '0', '功能演示', 2, null, '/function', 'Layout', null, null, null, 1, 12, 'menu', '', '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (90, 89, '0,89', 'Websocket', 1, null, '/function/websocket', 'demo/websocket', null, null, 1, 1, 3, '', '', '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (95, 36, '0,36', '字典组件', 1, null, 'dict-demo', 'demo/dictionary', null, null, 1, 1, 4, '', '', '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (97, 89, '0,89', 'Icons', 1, null, 'icon-demo', 'demo/icons', null, null, 1, 1, 2, 'el-icon-Notification', '', '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (102, 26, '0,26', 'document', 3, '', 'internal-doc', 'demo/internal-doc', null, null, null, 1, 1, 'document', '', '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (105, 2, '0,1,2', '用户列表', 4, null, '', null, 'sys:user:list', 0, 0, 1, 0, '', null, '2025-06-13 11:42:37', '2025-06-13 18:02:54', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (106, 2, '0,1,2', '用户导入', 4, null, '', null, 'sys:user:import', null, null, 1, 5, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (107, 2, '0,1,2', '用户导出', 4, null, '', null, 'sys:user:export', null, null, 1, 6, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (108, 36, '0,36', '增删改查', 1, null, 'curd', 'demo/curd/index', null, null, 1, 1, 0, '', '', null, null, null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (109, 36, '0,36', '列表选择器', 1, null, 'table-select', 'demo/table-select/index', null, null, 1, 1, 1, '', '', null, null, null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (110, 0, '0', '路由参数', 2, null, '/route-param', 'Layout', null, 1, 1, 1, 11, 'el-icon-ElementPlus', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (111, 110, '0,110', '参数(type=1)', 1, null, 'route-param-type1', 'demo/route-param', null, 0, 1, 1, 1, 'el-icon-Star', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', '{"type": "1"}');
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (112, 110, '0,110', '参数(type=2)', 1, null, 'route-param-type2', 'demo/route-param', null, 0, 1, 1, 2, 'el-icon-StarFilled', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', '{"type": "2"}');
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (117, 1, '0,1', '系统日志', 1, 'Log', 'log', 'system/log/index', null, 0, 1, 1, 6, 'document', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (118, 0, '0', '系统工具', 2, null, '/codegen', 'Layout', null, 0, 1, 1, 2, 'menu', null, '2025-06-13 11:42:37', '2025-06-18 14:43:25', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (119, 118, '0,118', '代码生成', 1, 'Codegen', 'codegen', 'codegen/index', null, 0, 1, 1, 1, 'code', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (120, 1, '0,1', '系统配置', 1, 'Config', 'config', 'system/config/index', null, 0, 1, 1, 7, 'setting', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (121, 120, '0,1,120', '系统配置查询', 4, null, '', null, 'sys:config:query', 0, 1, 1, 1, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (122, 120, '0,1,120', '系统配置新增', 4, null, '', null, 'sys:config:add', 0, 1, 1, 2, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (123, 120, '0,1,120', '系统配置修改', 4, null, '', null, 'sys:config:update', 0, 1, 1, 3, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (124, 120, '0,1,120', '系统配置删除', 4, null, '', null, 'sys:config:delete', 0, 1, 1, 4, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (125, 120, '0,1,120', '系统配置刷新', 4, null, '', null, 'sys:config:refresh', 0, 1, 1, 5, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (126, 1, '0,1', '通知公告', 1, 'Notice', 'notice', 'system/notice/index', null, null, null, 1, 9, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (127, 126, '0,1,126', '通知查询', 4, null, '', null, 'sys:notice:query', null, null, 1, 1, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (128, 126, '0,1,126', '通知新增', 4, null, '', null, 'sys:notice:add', null, null, 1, 2, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (129, 126, '0,1,126', '通知编辑', 4, null, '', null, 'sys:notice:edit', null, null, 1, 3, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (130, 126, '0,1,126', '通知删除', 4, null, '', null, 'sys:notice:delete', null, null, 1, 4, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (133, 126, '0,1,126', '通知发布', 4, null, '', null, 'sys:notice:publish', 0, 1, 1, 5, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (134, 126, '0,1,126', '通知撤回', 4, null, '', null, 'sys:notice:revoke', 0, 1, 1, 6, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (135, 1, '0,1', '字典项', 1, 'DictItem', 'dict-item', 'system/dict/dict-item', null, 0, 1, 0, 6, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (136, 135, '0,1,135', '字典项新增', 4, null, '', null, 'sys:dict-item:add', null, null, 1, 2, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (137, 135, '0,1,135', '字典项编辑', 4, null, '', null, 'sys:dict-item:edit', null, null, 1, 3, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (138, 135, '0,1,135', '字典项删除', 4, null, '', null, 'sys:dict-item:delete', null, null, 1, 4, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (139, 3, '0,1,3', '角色查询', 4, null, '', null, 'sys:role:query', null, null, 1, 1, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (140, 4, '0,1,4', '菜单查询', 4, null, '', null, 'sys:menu:query', null, null, 1, 1, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (141, 5, '0,1,5', '部门查询', 4, null, '', null, 'sys:dept:query', null, null, 1, 1, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (142, 6, '0,1,6', '字典查询', 4, null, '', null, 'sys:dict:query', null, null, 1, 1, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (143, 135, '0,1,135', '字典项查询', 4, null, '', null, 'sys:dict-item:query', null, null, 1, 1, '', null, '2025-06-13 11:42:37', '2025-06-13 11:42:37', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (144, 26, '0,26', '后端文档', 3, null, 'https://youlai.blog.csdn.net/article/details/145178880', '', null, null, null, 1, 3, 'document', '', '2024-10-05 23:36:03', '2024-10-05 23:36:03', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (145, 26, '0,26', '移动端文档', 3, null, 'https://youlai.blog.csdn.net/article/details/143222890', '', null, null, null, 1, 4, 'document', '', '2024-10-05 23:36:03', '2024-10-05 23:36:03', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (146, 36, '0,36', '拖拽组件', 1, null, 'drag', 'demo/drag', null, null, null, 1, 5, '', '', '2025-03-31 14:14:45', '2025-03-31 14:14:52', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (147, 36, '0,36', '滚动文本', 1, null, 'text-scroll', 'demo/text-scroll', null, null, null, 1, 6, '', '', '2025-03-31 14:14:49', '2025-03-31 14:14:56', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (148, 89, '0,89', '字典实时同步', 1, null, 'dict-sync', 'demo/dict-sync', null, null, null, 1, 3, '', '', '2025-03-31 14:14:49', '2025-03-31 14:14:56', null);
INSERT INTO sys_menu (id, parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params) VALUES (149, 2, '0,1,2', '用户状态修改', 4, null, null, null, 'sys:user:upstatus', 0, 1, 1, 1, null, null, '2025-07-11 17:08:53', '2025-07-11 17:08:53', null);

INSERT INTO sys_notice (id, title, content, type, level, target_type, target_user_ids, publisher_id, publish_status, publish_time, revoke_time, create_by, create_time, update_by, update_time, is_deleted) VALUES (1, 'v2.12.0 新增系统日志，访问趋势统计功能。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 1, 'L', 1, '2', 2, 1, '2025-06-17 10:43:43', '2025-06-17 10:43:10', 2, '2025-06-13 11:42:38', 2, '2025-06-13 11:42:38', 0);
INSERT INTO sys_notice (id, title, content, type, level, target_type, target_user_ids, publisher_id, publish_status, publish_time, revoke_time, create_by, create_time, update_by, update_time, is_deleted) VALUES (2, 'v2.13.0 新增菜单搜索。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 1, 'L', 1, '2', 1, 1, '2025-06-13 11:42:38', '2025-06-13 11:42:38', 2, '2025-06-13 11:42:38', 1, '2025-06-13 11:42:38', 0);
INSERT INTO sys_notice (id, title, content, type, level, target_type, target_user_ids, publisher_id, publish_status, publish_time, revoke_time, create_by, create_time, update_by, update_time, is_deleted) VALUES (3, 'v2.14.0 新增个人中心。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 1, 'L', 1, '2', 2, 1, '2025-06-13 11:42:38', '2025-06-13 11:42:38', 2, '2025-06-13 11:42:38', 2, '2025-06-13 11:42:38', 0);
INSERT INTO sys_notice (id, title, content, type, level, target_type, target_user_ids, publisher_id, publish_status, publish_time, revoke_time, create_by, create_time, update_by, update_time, is_deleted) VALUES (4, 'v2.15.0 登录页面改造。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 1, 'L', 1, '2', 2, 1, '2025-06-13 11:42:38', '2025-06-13 11:42:38', 2, '2025-06-13 11:42:38', 2, '2025-06-13 11:42:38', 0);
INSERT INTO sys_notice (id, title, content, type, level, target_type, target_user_ids, publisher_id, publish_status, publish_time, revoke_time, create_by, create_time, update_by, update_time, is_deleted) VALUES (5, 'v2.16.0 通知公告、字典翻译组件。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 1, 'L', 1, '2', 2, 1, '2025-06-13 11:42:38', '2025-06-13 11:42:38', 2, '2025-06-13 11:42:38', 2, '2025-06-13 11:42:38', 0);
INSERT INTO sys_notice (id, title, content, type, level, target_type, target_user_ids, publisher_id, publish_status, publish_time, revoke_time, create_by, create_time, update_by, update_time, is_deleted) VALUES (6, '系统将于本周六凌晨 2 点进行维护，预计维护时间为 2 小时。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 2, 'H', 1, '2', 2, 1, '2025-06-13 11:42:38', '2025-06-13 11:42:38', 2, '2025-06-13 11:42:38', 2, '2025-06-13 11:42:38', 0);
INSERT INTO sys_notice (id, title, content, type, level, target_type, target_user_ids, publisher_id, publish_status, publish_time, revoke_time, create_by, create_time, update_by, update_time, is_deleted) VALUES (7, '最近发现一些钓鱼邮件，请大家提高警惕，不要点击陌生链接。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 3, 'L', 1, '2', 2, 1, '2025-06-13 11:42:38', '2025-06-13 11:42:38', 2, '2025-06-13 11:42:38', 2, '2025-06-13 11:42:38', 0);
INSERT INTO sys_notice (id, title, content, type, level, target_type, target_user_ids, publisher_id, publish_status, publish_time, revoke_time, create_by, create_time, update_by, update_time, is_deleted) VALUES (8, '国庆假期从 10 月 1 日至 10 月 7 日放假，共 7 天。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 4, 'L', 1, '2', 2, 1, '2025-06-13 11:42:38', '2025-06-13 11:42:38', 2, '2025-06-13 11:42:38', 2, '2025-06-13 11:42:38', 0);
INSERT INTO sys_notice (id, title, content, type, level, target_type, target_user_ids, publisher_id, publish_status, publish_time, revoke_time, create_by, create_time, update_by, update_time, is_deleted) VALUES (9, '公司将在 10 月 15 日举办新产品发布会，敬请期待。', '公司将在 10 月 15 日举办新产品发布会，敬请期待。', 5, 'H', 1, '2', 2, 1, '2025-06-13 11:42:38', '2025-06-13 11:42:38', 2, '2025-06-13 11:42:38', 2, '2025-06-13 11:42:38', 0);
INSERT INTO sys_notice (id, title, content, type, level, target_type, target_user_ids, publisher_id, publish_status, publish_time, revoke_time, create_by, create_time, update_by, update_time, is_deleted) VALUES (10, 'v2.16.1 版本发布。', 'v2.16.1 版本修复了 WebSocket 重复连接导致的后台线程阻塞问题，优化了通知公告。', 1, 'M', 1, '2', 2, 1, '2025-06-13 11:42:38', '2025-06-13 11:42:38', 2, '2025-06-13 11:42:38', 2, '2025-06-13 11:42:38', 0);
INSERT INTO sys_notice (id, title, content, type, level, target_type, target_user_ids, publisher_id, publish_status, publish_time, revoke_time, create_by, create_time, update_by, update_time, is_deleted) VALUES (11, '重大升级通知', '<p>的点点滴滴</p>', 1, 'M', 1, null, 2, 1, '2025-06-19 17:29:51', null, 2, '2025-06-19 17:29:02', null, '2025-06-19 17:29:02', 0);

INSERT INTO sys_role (id, name, code, sort, status, data_scope, create_by, create_time, update_by, update_time, is_deleted) VALUES (1, '超级管理员', 'ROOT', 1, 1, 1, null, '2025-06-13 11:42:37', null, '2025-06-13 11:42:37', 0);
INSERT INTO sys_role (id, name, code, sort, status, data_scope, create_by, create_time, update_by, update_time, is_deleted) VALUES (2, '系统管理员', 'ADMIN', 2, 1, 1, null, '2025-06-13 11:42:37', null, null, 0);
INSERT INTO sys_role (id, name, code, sort, status, data_scope, create_by, create_time, update_by, update_time, is_deleted) VALUES (3, '访问游客', 'GUEST', 3, 1, 3, null, '2025-06-13 11:42:37', null, '2025-06-13 11:42:37', 0);

INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (177, 2, 1);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (178, 2, 2);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (187, 2, 3);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (192, 2, 4);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (197, 2, 5);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (202, 2, 6);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (237, 2, 9);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (238, 2, 10);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (226, 2, 11);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (227, 2, 12);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (233, 2, 13);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (234, 2, 14);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (235, 2, 15);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (236, 2, 16);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (230, 2, 17);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (229, 2, 18);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (228, 2, 19);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (246, 2, 20);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (247, 2, 21);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (248, 2, 22);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (249, 2, 23);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (250, 2, 24);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (241, 2, 26);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (243, 2, 30);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (180, 2, 31);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (182, 2, 32);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (183, 2, 33);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (251, 2, 36);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (254, 2, 37);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (255, 2, 38);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (256, 2, 39);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (239, 2, 40);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (240, 2, 41);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (189, 2, 70);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (190, 2, 71);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (191, 2, 72);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (193, 2, 73);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (195, 2, 74);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (196, 2, 75);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (198, 2, 76);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (200, 2, 77);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (201, 2, 78);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (203, 2, 79);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (205, 2, 81);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (206, 2, 84);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (184, 2, 88);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (263, 2, 89);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (266, 2, 90);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (257, 2, 95);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (264, 2, 97);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (242, 2, 102);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (179, 2, 105);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (185, 2, 106);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (186, 2, 107);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (252, 2, 108);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (253, 2, 109);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (260, 2, 110);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (261, 2, 111);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (262, 2, 112);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (207, 2, 117);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (231, 2, 118);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (232, 2, 119);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (213, 2, 120);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (214, 2, 121);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (215, 2, 122);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (216, 2, 123);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (217, 2, 124);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (218, 2, 125);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (219, 2, 126);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (220, 2, 127);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (221, 2, 128);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (222, 2, 129);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (223, 2, 130);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (224, 2, 133);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (225, 2, 134);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (208, 2, 135);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (210, 2, 136);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (211, 2, 137);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (212, 2, 138);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (188, 2, 139);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (194, 2, 140);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (199, 2, 141);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (204, 2, 142);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (209, 2, 143);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (244, 2, 144);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (245, 2, 145);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (258, 2, 146);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (259, 2, 147);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (265, 2, 148);
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES (181, 2, 149);

INSERT INTO sys_user (id, username, nickname, gender, password, dept_id, avatar, mobile, status, email, create_time, create_by, update_time, update_by, is_deleted, openid) VALUES (1, 'root', '有来技术', 0, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', null, 'https://foruda.gitee.com/images/1723603502796844527/03cdca2a_716974.gif', '18812345677', 1, 'youlaitech@163.com', '2025-06-13 11:42:38', null, '2025-06-13 11:42:38', null, 0, null);
INSERT INTO sys_user (id, username, nickname, gender, password, dept_id, avatar, mobile, status, email, create_time, create_by, update_time, update_by, is_deleted, openid) VALUES (2, 'admin', '系统管理员', 1, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', 1, 'https://foruda.gitee.com/images/1723603502796844527/03cdca2a_716974.gif', '18812345678', 1, 'youlaitech@163.com', '2025-06-13 11:42:38', null, '2025-06-13 11:42:38', null, 0, null);
INSERT INTO sys_user (id, username, nickname, gender, password, dept_id, avatar, mobile, status, email, create_time, create_by, update_time, update_by, is_deleted, openid) VALUES (3, 'test', '测试小用户', 1, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', 3, 'https://foruda.gitee.com/images/1723603502796844527/03cdca2a_716974.gif', '18812345679', 1, 'youlaitech@163.com', '2025-06-13 11:42:38', null, '2025-07-11 16:43:27', null, 0, null);

INSERT INTO sys_user_notice (id, notice_id, user_id, is_read, read_time, create_time, update_time, is_deleted) VALUES (1, 1, 2, 1, null, '2025-06-13 11:42:38', '2025-06-13 11:42:38', 1);
INSERT INTO sys_user_notice (id, notice_id, user_id, is_read, read_time, create_time, update_time, is_deleted) VALUES (2, 2, 2, 1, null, '2025-06-13 11:42:38', '2025-06-13 11:42:38', 0);
INSERT INTO sys_user_notice (id, notice_id, user_id, is_read, read_time, create_time, update_time, is_deleted) VALUES (3, 3, 2, 1, null, '2025-06-13 11:42:38', '2025-06-13 11:42:38', 0);
INSERT INTO sys_user_notice (id, notice_id, user_id, is_read, read_time, create_time, update_time, is_deleted) VALUES (4, 4, 2, 1, null, '2025-06-13 11:42:38', '2025-06-13 11:42:38', 0);
INSERT INTO sys_user_notice (id, notice_id, user_id, is_read, read_time, create_time, update_time, is_deleted) VALUES (5, 5, 2, 1, null, '2025-06-13 11:42:38', '2025-06-13 11:42:38', 0);
INSERT INTO sys_user_notice (id, notice_id, user_id, is_read, read_time, create_time, update_time, is_deleted) VALUES (6, 6, 2, 1, null, '2025-06-13 11:42:38', '2025-06-13 11:42:38', 0);
INSERT INTO sys_user_notice (id, notice_id, user_id, is_read, read_time, create_time, update_time, is_deleted) VALUES (7, 7, 2, 1, null, '2025-06-13 11:42:38', '2025-06-13 11:42:38', 0);
INSERT INTO sys_user_notice (id, notice_id, user_id, is_read, read_time, create_time, update_time, is_deleted) VALUES (8, 8, 2, 1, null, '2025-06-13 11:42:38', '2025-06-13 11:42:38', 0);
INSERT INTO sys_user_notice (id, notice_id, user_id, is_read, read_time, create_time, update_time, is_deleted) VALUES (9, 9, 2, 1, null, '2025-06-13 11:42:38', '2025-06-13 11:42:38', 0);
INSERT INTO sys_user_notice (id, notice_id, user_id, is_read, read_time, create_time, update_time, is_deleted) VALUES (10, 10, 2, 1, null, '2025-06-13 11:42:38', '2025-06-13 11:42:38', 0);
INSERT INTO sys_user_notice (id, notice_id, user_id, is_read, read_time, create_time, update_time, is_deleted) VALUES (11, 1, 1, 0, null, '2025-06-17 10:43:43', '2025-06-17 10:43:43', 0);
INSERT INTO sys_user_notice (id, notice_id, user_id, is_read, read_time, create_time, update_time, is_deleted) VALUES (12, 1, 2, 1, null, '2025-06-17 10:43:43', '2025-06-17 10:43:43', 0);
INSERT INTO sys_user_notice (id, notice_id, user_id, is_read, read_time, create_time, update_time, is_deleted) VALUES (13, 1, 3, 0, null, '2025-06-17 10:43:43', '2025-06-17 10:43:43', 0);
INSERT INTO sys_user_notice (id, notice_id, user_id, is_read, read_time, create_time, update_time, is_deleted) VALUES (14, 11, 1, 0, null, '2025-06-19 17:29:51', '2025-06-19 17:29:51', 0);
INSERT INTO sys_user_notice (id, notice_id, user_id, is_read, read_time, create_time, update_time, is_deleted) VALUES (15, 11, 2, 1, null, '2025-06-19 17:29:51', '2025-06-19 17:29:51', 0);
INSERT INTO sys_user_notice (id, notice_id, user_id, is_read, read_time, create_time, update_time, is_deleted) VALUES (16, 11, 3, 0, null, '2025-06-19 17:29:51', '2025-06-19 17:29:51', 0);

INSERT INTO sys_user_role (id, user_id, role_id) VALUES (1, 1, 1);
INSERT INTO sys_user_role (id, user_id, role_id) VALUES (2, 2, 2);
INSERT INTO sys_user_role (id, user_id, role_id) VALUES (3, 3, 3);


