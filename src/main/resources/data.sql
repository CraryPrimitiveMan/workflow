DELETE FROM PROCESS;

INSERT INTO PROCESS (process_id, process_name, process_alias) VALUES
(1, 'and_case', '所有满足案例'),
(2, 'or_case', '任意满足案例'),
(3, 'complex_case', '复杂案例');


DELETE FROM TASK;

INSERT INTO TASK (task_id, task_name, process_id, node_type, process_logic, role_id) VALUES
-- 所有满足案例
(1, '请假审批开始', 1, 1, 0, 1),
(2, '组长审批', 1, 3, 0, 2),
(3, '总监审批', 1, 3, 0, 3),
(4, '请假审批完成', 1, 2, 1, 0),
-- 任意满足案例
(5, '代码审查开始', 2, 1, 0, 1),
(6, '组长审查', 2, 3, 0, 2),
(7, '总监审查', 2, 3, 0, 3),
(8, '代码审查完成', 2, 3, 2, 0)
-- 复杂满足案例
-- (5, '核心代码审查开始', 3, 1, 0, 1),
-- (6, '组员审查', 3, 3, 0, 1),
-- (6, '组员审查', 3, 3, 0, 1),
-- (7, '总监审查', 3, 3, 0, 3),
-- (8, '代码审查完成', 3, 3, 4, 0),
;

DELETE FROM TASK_ROUTE;

INSERT INTO TASK_ROUTE (id, process_id, task_id, next_task_id) VALUES
(1, 1, 1, 2),
(2, 1, 1, 3),
(3, 1, 2, 4),
(4, 1, 3, 4),

(5, 2, 5, 6),
(6, 2, 5, 7),
(7, 2, 6, 8),
(8, 2, 7, 8)

;

DELETE FROM RELATION_USER_ROLE;

INSERT INTO RELATION_USER_ROLE (user_id, role_id) VALUES
-- 组员
(1, 1),
(2, 1),

-- 组长
(3, 2),
(4, 2),

-- 总监
(5, 3),
(6, 3)

;