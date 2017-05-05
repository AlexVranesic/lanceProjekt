package si.triglav.hackathon.person;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDAO {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final String PERSON_COLUMN_LIST = "id,firstname,lastname,date_of_birth,address,ts_create,created_by";
	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<Person> getPersonList() {
		List<Person> personList = jdbcTemplate.query("select "+PERSON_COLUMN_LIST+" from hack.PERSON", new BeanPropertyRowMapper<Person>(Person.class));
		return personList;
	}
	
	public Person getPersonById(Integer id) {
		MapSqlParameterSource params = new MapSqlParameterSource("id", id);
		Person person = jdbcTemplate.queryForObject("select "+PERSON_COLUMN_LIST+" from hack.PERSON where id = :id", params , new BeanPropertyRowMapper<Person>(Person.class));
		return person;
	}
	
	

	public Person createPerson(Person person) {
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
				"insert into hack.person (firstname,lastname,date_of_birth,address,created_by) values (:firstname,:lastname,:date_of_birth,:address,:created_by)",
				new BeanPropertySqlParameterSource(person), generatedKeyHolder);
		
		Person createdPeson = getPersonById(generatedKeyHolder.getKey().intValue());
		return createdPeson;

	}

	public int updatePerson(Person person) {
		int updatedRowsCount = jdbcTemplate.update(
				"update hack.person set (firstname,lastname,date_of_birth,address) = (:firstname,:lastname,:date_of_birth,:address) where id = :id",
				new BeanPropertySqlParameterSource(person));
		return updatedRowsCount;
		
	}

	public int deletePerson(Integer id) {
		int deletedRows = jdbcTemplate.update("delete from hack.person where id = :id", new MapSqlParameterSource("id", id));
		return deletedRows;
	}

}
