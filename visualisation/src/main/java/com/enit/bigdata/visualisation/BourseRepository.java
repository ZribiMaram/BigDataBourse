package com.enit.bigdata.visualisation;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BourseRepository extends CassandraRepository<BourseAction, String> {

 
 
}
