package cn.weiyunmei.controller.user;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import cn.weiyunmei.entity.user.UserConsumeLog;
import cn.weiyunmei.spring.view.RestView;
import cn.weiyunmei.support.container.QueryContainer;
import cn.weiyunmei.support.controller.RestController;

@Controller
@RequestMapping("/user/consume/log")
public class UserConsumeLogController extends RestController<UserConsumeLog> {
	
	/**
	 * 获取用户收益明细
	 */
	@Override
	public View list(UserConsumeLog entity, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = entity.getUser().getId();
		QueryContainer qc = new QueryContainer();
		qc.addCondition("user.id="+userId);
		List<UserConsumeLog> userConsumeLogs = getBaseDao().findByQueryContainer(qc);
		return new RestView(userConsumeLogs, null);
	}
	
	/**
	 * 获取粉丝收益
	 */
	@RequestMapping(value="/fans.do",method=RequestMethod.GET,params={"user.id"})
	public View fans(@RequestParam("user.id")String userId){
		QueryContainer qc = new QueryContainer();
		qc.addCondition("user.id="+userId);
		qc.addCondition("type="+UserConsumeLog.TYPE_FANS);
		List<UserConsumeLog> userConsumeLogs = getBaseDao().findByQueryContainer(qc);
		
		int count = 0;
		for(UserConsumeLog userConsumeLog : userConsumeLogs){
			count+= userConsumeLog.getMoney();
		}
		
		return new RestView(count,null);
	}
	
	/**
	 * 获取今日收益
	 */
	@RequestMapping(value="/today.do",method=RequestMethod.GET,params={"user.id"})
	public View getConsume4today(@RequestParam("user.id")String userId){
		long startTime = 0;
		long endTime = new Date().getTime();
		Calendar start = Calendar.getInstance();
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MILLISECOND,0);
		start.set(Calendar.SECOND, 0);
		startTime = start.getTimeInMillis();
		return new RestView(getConsumenCount4Time(startTime, endTime,userId),null);
	}
	
	/**
	 * 获取昨日收益
	 */
	@RequestMapping(value="/yesterday.do",method=RequestMethod.GET,params={"user.id"})
	public View getConsume4yesterday(@RequestParam("user.id")String userId){
		long startTime = 0;
		Calendar start = Calendar.getInstance();
		start.add(Calendar.DAY_OF_MONTH, -1);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MILLISECOND,0);
		start.set(Calendar.SECOND, 0);
		startTime = start.getTimeInMillis();
		
		long endTime = 0;
		Calendar end = Calendar.getInstance();
		end.add(Calendar.DAY_OF_MONTH, -1);
		end.set(Calendar.HOUR_OF_DAY, 23);
		end.set(Calendar.MILLISECOND,59);
		end.set(Calendar.SECOND, 59);
		endTime = end.getTimeInMillis();
		return new RestView(getConsumenCount4Time(startTime, endTime,userId),null);
	}
	
	private int getConsumenCount4Time(long startTime,long endTime,String userId){
		int count = 0;
		QueryContainer qc = new QueryContainer();
		qc.addCondition("createTime BETWEEN "+startTime+" AND "+endTime);
		qc.addCondition("user.id="+userId);
		List<UserConsumeLog> userConsumeLogs = getBaseDao().findByQueryContainer(qc);
		
		for(UserConsumeLog userConsumeLog : userConsumeLogs){
			count+= userConsumeLog.getMoney();
		}
		
		return count;
	}
	
	/**
	 * 获取累计收益
	 */
	@RequestMapping(value="/count.do",method=RequestMethod.GET,params={"user.id"})
	public View getCount(@RequestParam("user.id")String userId){
		int count = 0;
		List<UserConsumeLog> userConsumeLogs = getBaseDao().findAll();
		
		for(UserConsumeLog userConsumeLog : userConsumeLogs){
			count+= userConsumeLog.getMoney();
		}
		return new RestView(count,null);
	}
	
	
	
}
