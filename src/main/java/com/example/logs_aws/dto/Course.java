package com.example.logs_aws.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="course", schema="test")
public class Course {

	@Id
	private Integer id;
	
	private String name;
	
}
