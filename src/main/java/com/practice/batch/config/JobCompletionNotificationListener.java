package com.practice.batch.config;
import java.util.List;

import com.practice.batch.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		RowMapper<Employee> rowMapper = (rs, rowNum) -> {

			Employee e = new Employee();

			e.setFirstName(rs.getString(1));
			e.setLastName(rs.getString(2));
			return e;
		};
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");

		List<Employee> empList= jdbcTemplate.query("SELECT first_name, last_name FROM employee",rowMapper);
		log.info("Size of List "+empList.size());
		for (Employee emp: empList) {
			log.info("Found: "+emp.getFirstName()+" "+emp.getLastName());
			
		}
		}
	}
}