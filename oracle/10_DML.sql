 /*
    DML(Data Manipulation Language)
    : 데이터 조작 언어로 테이블에 값을 삽입(INSERT) 하거나,
      수정(UPDATE)하거나, 삭제(DELETE)하는 구문
      
      
    INSERT
    
    - 테이블에 새로운 행을 추가하는 구문
    
    [표현법]
    1) INSERT INTO 테이블 명 VALUES(값1, 값2, ...., 값N);
    
    
        - 테이블에 모든 컬럼에 대한 값을 INSERT 하고자 할 때 사용
        
        - 컬럼에 순번을 지켜서 VALUES 값을 나열해야함
    
    
    2) INSERT INTO 테이블명(컬럼명1, 컬럼명2, ..., 컬럼명N) VALUES(값1, 값2, ...., 값N);
    
    
        - 테이블 특정 컬럼에 대한 값만 INSERT 하고자 할 때 사용
        
        - 선택 안된 컬럼들은 기본적으로 NULL 값이 들어감(NOT NULL 일때는 반드시 넣어주어야함)
        
        - 기본값(DEFAULT)이 지정되어 있으면 기본값으로 들어감
        
    3) INSERT INTO 테이블 명 (서브쿼리);
        
        
        - VALUES 대신 서브쿼리로 조회한 결과 값을 통째로 INSERT
        
        - 서브쿼리의 결과 값이 INSERT 문에 지정된
          테이블 컬럼의 개수와 데이터 타입이 같아야 함
*/
​
DROP TABLE EMP_01;
​
CREATE TABLE EMP_01(
    EMP_ID NUMBER PRIMARY KEY,
    EMP_NAME VARCHAR2(30) NOT NULL,
    DEPT_TITLE VARCHAR2(30),
    HIRE_DATE DATE DEFAULT SYSDATE
);
​
-- 표현법 1)
​
INSERT INTO EMP_01
VALUES (100, '이정재', '서비스 개발팀', DEFAULT);
​
INSERT INTO EMP_01
VALUES (200, '이병헌', '개발 지원팀');
--> 모든 컬럼에 값을 지정하지 않아서 에러 발생
​
INSERT INTO EMP_01
VALUES (300, '유아지원팀', '공유', DEFAULT);
--> 에러는 발생하지 않지만 데이터가 잘못 저장됨
​
INSERT INTO EMP_01
VALUES ('유아지원팀', 300, '위하준', DEFAULT);
-- 컬럼 순서와 데이터타입 순서가 일치하지 않음
​
​
-- 표현법 2)
​
INSERT INTO EMP_01(EMP_ID, EMP_NAME, DEPT_TITLE, HIRE_DATE)
VALUES (400, '임시완', '인사팀', NULL);
​
INSERT INTO EMP_01(EMP_NAME, EMP_ID, DEPT_TITLE, HIRE_DATE)
VALUES ('임시완', 500, '보안팀', SYSDATE);
​
INSERT INTO EMP_01(EMP_ID, EMP_NAME)
VALUES (600, '박성훈');
​
INSERT INTO EMP_01(EMP_ID, DEPT_TITLE)
VALUES (700, '마케팅팀');
--> EMP_NAME 컬럼이 NOT NULL 제약조건이 걸려있어서 에러가 발생한다.
​
INSERT INTO EMP_01(EMP_ID, EMP_NAME, DEPT_TITLE)
VALUES (700, '양동근', '마케팅팀');
​
​
​
​
​
-- 표현법 3)
-- EMPLOYEE 테이블에서 직원의 사번, 이름, 부서명, 입사일을 조회해서
-- EMP_01 테이블에 INSERT 하시오.
INSERT INTO EMP_01(SELECT E.EMP_ID, E.EMP_NAME, D.DEPT_TITLE, E.HIRE_DATE 
                    FROM EMPLOYEE E
                    JOIN DEPARTMENT D ON(E.DEPT_CODE = D.DEPT_ID));
​
​
INSERT INTO EMP_01(EMP_ID, EMP_NAME)(SELECT EMP_ID, EMP_NAME 
                                    FROM EMPLOYEE);
​
​
SELECT * FROM EMP_01;
​
​
 /*
    INSERT ALL
    두 개 이상의 테이블에 각각 INSERT 하는데 동일한 서브 쿼리가 사용되는 경우
    INSERT ALL을 이용해서 여러 테이블에 한 번에 데이터 삽입가능
    
    [표현법]
    
    
    1) INSERT ALL
       INTO 테이블명1[(컬럼1, 컬럼2, ...)] VALUES(값1, 값2, ...)
       INTO 테이블명2[(컬럼1, 컬럼2, ...)] VALUES(값1, 값2, ...)
       서브쿼리;
       
    
    2) INSERT ALL
       WHEN 조건1 THEN
            INTO 테이블명1[(컬럼1, 컬럼2, ...)] VALUES(값1, 값2, ...)
       WHEN 조건2 THEN
            INTO 테이블명2[(컬럼1, 컬럼2, ...)] VALUES(값1, 값2, ...)
       서브쿼리;
*/
​
-- 표현법 1)
​
DROP TABLE EMP_DEPT;
DROP TABLE EMP_MANAGER;
​
CREATE TABLE EMP_DEPT
AS SELECT EMP_ID, EMP_NAME, DEPT_CODE, HIRE_DATE FROM EMPLOYEE
WHERE 1=0;
​
CREATE TABLE EMP_MANAGER
AS SELECT EMP_ID, EMP_NAME, MANAGER_ID FROM EMPLOYEE
WHERE 1=0;
​
SELECT * FROM EMP_DEPT;
SELECT * FROM EMP_MANAGER;
​
-- EMP_DEPT 테이블에 EMPLOYEE 테이블의 부서코드가 D1인 직원의 사번, 이름, 부서코드, 입사일 삽입하고,
-- EMP_MANAGER 테이블에 EMPLOYEE 테이블의 부서코드가 D1인 직원의 사번, 이름, 관리자 사번을 조회하고 삽입한다.
​
SELECT EMP_ID, EMP_NAME, DEPT_CODE, HIRE_DATE, MANAGER_ID
FROM EMPLOYEE
WHERE DEPT_CODE = 'D1';
​
INSERT ALL INTO EMP_DEPT VALUES(EMP_ID, EMP_NAME, DEPT_CODE, HIRE_DATE)
INTO EMP_MANAGER VALUES(EMP_ID, EMP_NAME, MANAGER_ID)
    SELECT EMP_ID, EMP_NAME, DEPT_CODE, HIRE_DATE, MANAGER_ID
    FROM EMPLOYEE
    WHERE DEPT_CODE = 'D1';
    
SELECT * FROM EMP_DEPT;
SELECT * FROM EMP_MANAGER;
​
​
-- 표현법 2)
DROP TABLE EMP_OLD;
DROP TABLE EMP_NEW;

CREATE TABLE EMP_OLD
AS SELECT EMP_ID, EMP_NAME, HIRE_DATE, SALARY
        FROM EMPLOYEE
        WHERE 0=1;
        
CREATE TABLE EMP_NEW
AS SELECT EMP_ID, EMP_NAME, HIRE_DATE, SALARY
        FROM EMPLOYEE
        WHERE 0=1;
        
SELECT * FROM EMP_OLD;
SELECT * FROM EMP_NEW;
​
-- EMPLOYEE 테이블의 입사일 기준으로 2000.01.01 이전에
-- 입사한 사원의 정보는 EMP_OLD 테이블에 삽입하고,
-- 2000.01.01 이후에 입사한 사원의 정보는 EMP_NEW 테이블에 삽입한다.

INSERT ALL 
    WHEN HIRE_DATE > '00/01/01' THEN INTO EMP_NEW VALUES(EMP_ID, EMP_NAME, HIRE_DATE, SALARY)
    ELSE INTO EMP_OLD VALUES(EMP_ID, EMP_NAME, HIRE_DATE, SALARY)   
SELECT EMP_ID, EMP_NAME, HIRE_DATE, SALARY
FROM EMPLOYEE;

SELECT * FROM EMP_OLD;
SELECT * FROM EMP_NEW;




 /*
    UPDATE
    테이블에 기록된 데이터를 수정하는 구문
    
    [표현법]
    
    UPDATE 테이블명
    SET 컬럼명 = 변경하려는값 OR (서브쿼리),
        컬럼명 = 변경하려는값 OR (서브쿼리),
        ...
        [WHERE 조건];
        
    - SET절에서 여러 개의 컬럼을 콤마(,)로 나열해서 값을 동시에 변경할 수 있다.
    
    - WHERE절을 생략하면 모든 행의 데이터가 변경된다.
    
    - UPDATE시 서브쿼리를 사용해서 서브쿼리를 수행한 결과값으로 컬럼값을 변경할 수 있다.
*/
DROP TABLE DEPT_COPY;
DROP TABLE EMP_SALARY;

CREATE TABLE DEPT_COPY
AS SELECT * FROM DEPARTMENT;
​
CREATE TABLE EMP_SALARY
AS SELECT EMP_ID, EMP_NAME, DEPT_CODE, SALARY, BONUS FROM EMPLOYEE;
​
SELECT * FROM DEPT_COPY;
SELECT * FROM EMP_SALARY;
​
​
-- DEPT_COPY 테이블에서 DEPT_ID 가 'D9'인 부서명을 '전략기획팀'으로 수정 (총무부 -> 전략기획팀)
​
UPDATE DEPT_COPY
SET DEPT_TITLE = '전략기획팀'
WHERE DEPT_ID = 'D9';
​
ROLLBACK;
​
SELECT * FROM DEPT_COPY;
SELECT * FROM EMP_SALARY;
​
-- EMP_SALARY 테이블에서 노옹철 사원의 급여를 100원으로 변경
UPDATE EMP_SALARY
SET SALARY = 100
WHERE EMP_NAME = '노옹철';
​
SELECT * FROM EMP_SALARY;
​
-- EMP_SALARY 테이블에서 선동일 사원의 급여를 7000원으로, 보너스를 0.2로 변경
UPDATE EMP_SALARY
SET SALARY = 7000, BONUS = 0.2
WHERE EMP_NAME = '선동일';
​
SELECT * FROM EMP_SALARY;
​
-- 모든 사원의 급여를 기존 급여에서 10프로 인상한 급여 (기존급여 * 1.1)로 변경
UPDATE EMP_SALARY
SET SALARY = 1.1 * SALARY;
​
SELECT * FROM EMP_SALARY;


UPDATE EMP_SALARY
SET EMP_ID = NULL -- NOT NULL 제약조건에 위배
WHERE EMP_NAME = '노옹철';

--UPDATE EMP_SALARY
--SET DEPT_CODE = 'D0' -- FOREIGN KEY 제약조건 위배
--WHERE EMP_NAME = '노옹철';

-- UPDATE 시 서브쿼리 사용 가능
-- 방명수 사원의 급여와 보너스를 유재식 사원과 동일하게 변경

SELECT * FROM EMP_SALARY;

UPDATE EMP_SALARY
SET (SALARY, BONUS) = (SELECT SALARY, BONUS FROM EMP_SALARY WHERE EMP_NAME = '유재식')
WHERE EMP_NAME = '방명수';

SELECT * FROM EMP_SALARY;


UPDATE EMP_SALARY
SET BONUS = 0.3
WHERE DEPT_CODE IN (
SELECT DISTINCT D.DEPT_ID
FROM DEPT_COPY D
JOIN LOCATION L ON(D.LOCATION_ID = L.LOCAL_CODE)
WHERE LOCAL_NAME LIKE 'ASIA%');

SELECT * FROM EMP_SALARY;


 /*
    DELETE
    테이블에 기록된 데이터를 삭제하는 구문(행 단위로 삭제)
    
    [표현법]
    
    DELETE FROM 테이블명
    [WHERE 조건식];
    
    - WHERE절을 제시하지 않으면 전체 행이 삭제
*/
​
​
-- EMP_SALARY 테이블에서 선동일 사장의 데이터를 지우기
​
SELECT * FROM EMP_SALARY;
​
DELETE FROM EMP_SALARY WHERE EMP_NAME = '선동일'; -- OR EMP_ID = 200;
​
ROLLBACK;
​
​
-- EMP_SALARY 테이블에서 DEPT_CODE 가 D5인 직원들을 삭제
​
SELECT * FROM EMP_SALARY WHERE DEPT_CODE = 'D5';
​
DELETE FROM EMP_SALARY WHERE DEPT_CODE = 'D5';
​
ROLLBACK;
​
​
-- DEPT_COPY 테이블에서 DEPT_ID가 D1인 부서 삭제 (되면 안됨)
​
DELETE FROM DEPT_COPY WHERE DEPT_ID = 'D1';
​
​
 /*
    TRUNCATE
    
    
    테이블의 전체 행을 삭제할 때 사용하는 구문
    
    - DELETE 보다 수행속도가 더 빠르다.
    
    - 별도의 조건을 제시할 수 없고, ROLLBACK이 불가능
    
    [표현법]
    
    TRUNCATE TABLE 테이블 명;
*/
​
DELETE FROM DEPT_COPY;
DELETE FROM EMP_SALARY;
​
SELECT * FROM DEPT_COPY;
SELECT * FROM EMP_SALARY;
​
​
ROLLBACK;
​
TRUNCATE TABLE DEPT_COPY;
TRUNCATE TABLE EMP_SALARY;
​
DROP TABLE DEPT_COPY;
DROP TABLE EMP_SALARY;














