package AOPDemo.aspect;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import AOPDemo.apps.Account;

@Aspect
@Component
@Order(1)
public class MyDemoLoggingAspect {
	
	@Around("execution(* AOPDemo.service.TrafficFortuneService.getFortune(..))")	
	public Object aroundGetFortune(ProceedingJoinPoint theProceedingJoinPint) throws Throwable {
		
		//print out method we are advising on
		String method = theProceedingJoinPint.getSignature().toShortString();
		System.out.println("\n=>>======>> Executing @Around () advice on method: " + method);	
		
		//get begin timestamp
		double begin = System.currentTimeMillis();
		
		//let's execute the method
		Object result = theProceedingJoinPint.proceed();
		
		//end the timestamp
		double end = System.currentTimeMillis();
		
		//compute duration and display it
		double duration = end - begin;
		System.out.println("\n=>>======>> Duration: " + duration/1000);	 // x/1000 miliseconds to second
		
		return result;
	}	
	
//-------------------------------------------------------------------------------------------------------------		
	@After("execution(* AOPDemo.dao.AccountDAO.findAccounts(..))")
	public void afterFinallyFindAccountAdvice(JoinPoint theJoinPoint) {
		
		//print out which method we are advising on
		String method = theJoinPoint.getSignature().toShortString();
		System.out.println("\n<<====<<=== Executing @After () advice on method: " + method);	
	}	
	
//-------------------------------------------------------------------------------------------------------------	
	@AfterThrowing(
			pointcut = "execution(* AOPDemo.dao.AccountDAO.findAccounts(..))",
			throwing = "theExc")
	public void afterThrowingFindAccountAdvice(JoinPoint theJoinPoint, Throwable theExc) {
		
		//print out which method we are advising on
		String method = theJoinPoint.getSignature().toShortString();
		System.out.println("\n<<==<<===== Executing @AfterThrowing advice on method: " + method);	
		
		//log the exception
		System.out.println("\n<<==<<===== The exception is: " + theExc);	
	}
	
//-------------------------------------------------------------------------------------------------------------
	@AfterReturning(
			pointcut = "execution(* AOPDemo.dao.AccountDAO.findAccounts(..))",
			returning = "result")
	public void afterReturningFindAccountAdvice(JoinPoint theJoinPoint, List<Account> result) {
		
		//print out which method we are advising on
		String method = theJoinPoint.getSignature().toShortString();
		System.out.println("\n<<<<======= Executing @AfterReturning advice on method: " + method);
		
		//print out results of the method call
		System.out.println("<<<<======= Result is: " + result);
		
		//lets post-process the data..let's modify it :)
		
		//convert the account names to upperCase
		convertAccontNamesToUpperCase(result);
		System.out.println("<<<<======= Result is: " + result);
	}
	
//-------------------------------------------------------------------------------------------------------------	
	private void convertAccontNamesToUpperCase(List<Account> result) {
		
		// loop through accounts
		for (Account tempAccount : result) {
		
			// get upperCase version of name 
			String theUpperName = tempAccount.getName().toUpperCase();
				
			// update the name on the account
			tempAccount.setName(theUpperName);
		}	
	}

//-------------------------------------------------------------------------------------------------------------
//When we put pointcut declaration in separate class we have to give @Before fully qualified class name.
	
	@Before("AOPDemo.aspect.AopExpressions.forDaoPackageNoGetterSetter()") 								
	public void beforeAddAccountAdvice(JoinPoint theJoinPoint) {
		System.out.println("\n=======>>>> Executing @Before advice on method");
		
		//display the method signature using join(access) point
		MethodSignature methodSig = (MethodSignature) theJoinPoint.getSignature();
		System.out.println("Method: " + methodSig);
		
		//display method arguments
		
		//get arguments
		Object[] args = theJoinPoint.getArgs();
		
		//loop thru arguments
		for (Object tempArg : args) {
			System.out.println(tempArg);
			
			if(tempArg instanceof Account) {
				//downcast and print Account specific stuff
				Account theAccount = (Account) tempArg;
				
				System.out.println("account name: " + theAccount.getName());
				System.out.println("account level: " + theAccount.getLevel());
			}
			
		}
		
	}

}
 