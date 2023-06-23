/*
    서브쿼리(SUBQUERY)
     하나의 SQL문 안에 포함된 또 다른 SQL문
*/
-- 간단 서브쿼리 예시1
-- 노옹철 사원과 같은 부서원들을 조회
-- 1) 먼저 노옹철 사원의 부서코드 조회
SELECT DEPT_CODE
FROM EMPLOYEE
WHERE EMP_NAME = '노옹철'; -- D9
-- 2) 부서코드가 D9인 사원들을 조회
SELECT EMP_NAME
FROM EMPLOYEE
WHERE DEPT_CODE = 'D9';
--> 위에 두 쿼리 병합
SELECT EMP_NAME
FROM EMPLOYEE
WHERE DEPT_CODE = ( SELECT DEPT_CODE
                                        FROM EMPLOYEE
                    WHERE EMP_NAME = '노옹철'
                  );
-- 전 직원의 평균 급여보다 더 많은 급여를 받는 사원들의 사번, 사원명, 직급코드, 급여조회
SELECT EMP_ID, EMP_NAME, JOB_CODE, SALARY
FROM EMPLOYEE
WHERE SALARY > ( SELECT AVG(SALARY)
                 FROM EMPLOYEE
               );
               
               
/*
    서브쿼리의 분류
    - 서브쿼리를 수행한 결과 갑이 몇 팽 몇 열이냐에 따라서 분류됨
    - 서브쿼리의 종류에 따라 서브쿼리 앞에 붙는 연산자가 달라짐
*/
/*
    1. 단일행 서브쿼리(SINGLE ROW SUBQUERY)
    - 서브쿼리의 조회 결과갑의 개수가 오로지 1개일 때 (한 행, 한 열)
    - 일반 비교연산자 사용 가능 : =, !=, ^=, >, <, >=, ...
*/

-- 노옹철 사원의 급여보다 더 많이 받는 사원의 사번, 사원명, 부서명, 직급 코드, 급여 조회
SELECT EMP_ID, EMP_NAME, DEPT_TITLE, JOB_CODE, SALARY
FROM EMPLOYEE E
    JOIN DEPARTMENT D ON(E.DEPT_CODE = D.DEPT_ID)
WHERE SALARY > (SELECT SALARY FROM EMPLOYEE WHERE EMP_NAME LIKE '노옹철');

-- 부서별 급여의 합이 가장 큰 부서의 부서 코드, 급여의 합 조회
SELECT
DEPT_CODE, SUM(SALARY)
FROM EMPLOYEE
GROUP BY DEPT_CODE 
HAVING SUM(SALARY) = (SELECT MAX(SUM(SALARY)) FROM EMPLOYEE GROUP BY DEPT_CODE);

-- 전지연 사원이 속해있는 부서원들의 사번, 사원명, 전화번호, 직급명, 
-- 부서명, 입사일 조회(단, 전지연 사원은 제외)

SELECT
EMP_ID, EMP_NAME, PHONE, E.JOB_CODE, JOB_NAME, E.DEPT_CODE, DEPT_TITLE, HIRE_DATE
FROM EMPLOYEE E
JOIN DEPARTMENT D ON(E.DEPT_CODE = D.DEPT_ID)
JOIN JOB J ON(J.JOB_CODE = E.JOB_CODE)
WHERE EMP_NAME != '전지연' AND DEPT_CODE = (
SELECT DEPT_CODE
FROM EMPLOYEE
WHERE EMP_NAME = '전지연');

