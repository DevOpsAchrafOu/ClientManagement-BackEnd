/**
 * 
 */
package ma.mang.be.api.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ma.mang.be.api.dto.ParPaysDto;
import ma.mang.be.api.model.ParPays;
import ma.mang.be.api.service.ParPaysService;

/**
 * REST Api to manipulate ParPays objects
 * @author achraf
 * @version v0.3
 */
@RestController
@RequestMapping("api/mang/v1")
@Api(tags = { "01 - ParPayss : REST Interfaces to manipulate ParPays objects" })
public class ParPaysRest {
	
	private static final String MSG_FAILED_REQUEST= "Request failed. Please try later!";
	private static final String MSG_PAYS_CREATED = "ParPays successfully created.";
	private static final String MSG_PAYS_UPDATED = "ParPays successfully updated.";

	@Autowired
	private ParPaysService paysService;

	

	@PostMapping("/pays")
	@ApiOperation(notes = "Creates a pays ", value = "", response = ParPaysDto.class)
	public ResponseEntity<?> addParPays(@RequestBody ParPaysDto paysDetails)
			throws Exception {
		ParPays m =null;
		try {
		 m = paysService.save(ParPaysDto.convertToEntity(paysDetails));
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(ParPaysDto.convertToDto(m));
	}
	
	@GetMapping("/pays")
	@ApiOperation(notes = "Retrieves all pays", value = "", response = ParPays.class)
	public ResponseEntity<List<ParPaysDto>> getParPayss() throws Exception {
		List<ParPays> pays = paysService.getAllParPayss();
		return ResponseEntity.ok(ParPaysDto.convertToDto(pays));
	}


	@GetMapping("/pays/{id}")
	@ApiOperation(notes = "Retrieves pays by ID", value = "", response = ParPays.class)
	public ResponseEntity<ParPaysDto> getParPaysById(@PathVariable(value = "id") Long paysId) throws Exception {
		ParPays pays = paysService.getParPaysById(paysId);
		if (pays == null) {
			throw new Exception("ParPays not found for this id :: " + paysId);
		}
	
		return ResponseEntity.ok(ParPaysDto.convertToDto(pays));
	}


	@PutMapping("/pays/{id}")
	@ApiOperation(notes = "Updates a pays identified by ID", value = "", response = String.class)
	public ResponseEntity<?> updateParPays(@PathVariable(value = "id") Long paysId, @RequestBody ParPaysDto paysDto)
			throws Exception {
		ParPays pays = paysService.getParPaysById(paysId);
		String rst="";
		String msg="";
		if(pays==null) {
			new Exception("ParPays not found for this id :: " + paysId);
		}
		ParPays m = paysService.save(ParPaysDto.convertToEntity(paysDto));
		if(m!=null) {
			msg = MSG_PAYS_UPDATED;
		}else {
			throw new Exception(MSG_FAILED_REQUEST);
		}
		return ResponseEntity.ok(msg);
	}
	

	@DeleteMapping("/pays/{id}")
	@ApiOperation(notes = "Deletes a pays identified by ID from database", value = "", response = String.class)
	public ResponseEntity<?> deleteParPays(@PathVariable(value = "id") Long paysId) throws Exception {
		ParPays pays = paysService.getParPaysById(paysId);
		if(pays==null) {
			new Exception("ParPays not found for this id :: " + paysId);
		}
		paysService.delete(paysId);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
