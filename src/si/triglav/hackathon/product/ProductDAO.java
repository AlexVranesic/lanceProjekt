package si.triglav.hackathon.product;

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

import si.triglav.hackathon.team.TeamDAO;

@Repository
public class ProductDAO {

	@Autowired
	private TeamDAO teamDAO;
	
	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final String PRODUCT_COLUMN_LIST = "id_product,product";
	private static final String TABLE_NAME = "FREELANCE.PRODUCT";
	
	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<Product> getProductList() {
		List<Product> productList = jdbcTemplate.query("select " + PRODUCT_COLUMN_LIST + " from " + TABLE_NAME, new BeanPropertyRowMapper<Product>(Product.class));
		return productList;
	}
	
	public Product getProductById(Integer id_product) {
		MapSqlParameterSource params = new MapSqlParameterSource("id_product", id_product);
		Product product = jdbcTemplate.queryForObject("select "+PRODUCT_COLUMN_LIST+" from "+ TABLE_NAME + " where id_product = :id_product", params , new BeanPropertyRowMapper<Product>(Product.class));
		return product;
	}
	
	public Product createProduct(Product product) {
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
				"insert into "+TABLE_NAME+" (product) VALUES (:product)",
				new BeanPropertySqlParameterSource(product), generatedKeyHolder);
		
		Product createdProduct = getProductById(generatedKeyHolder.getKey().intValue());
		return createdProduct;
	}

	public int updateProduct(Product product) {
		int updatedRowsCount = jdbcTemplate.update(
				"update "+TABLE_NAME+" set (product) = (:product) where id_product = :id_product",
				new BeanPropertySqlParameterSource(product));
		return updatedRowsCount;
		
	}

	public int deleteProduct(Integer id_product) {
		int deletedRows = jdbcTemplate.update("delete from "+TABLE_NAME+" where id_product = :id_product", new MapSqlParameterSource("id_product", id_product));
		return deletedRows;
	}
}