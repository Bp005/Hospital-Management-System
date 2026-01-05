package com.kec.hms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.kec.hms.model.Bill;

@RestController
@RequestMapping("/api/v1/bills")
public class BillController {
	
	@GetMapping
	public List<Bill> getallBill(){
		System.out.println("fetching the bill");
		return null;
	}
	
	@PostMapping
	public Bill createBill(@RequestBody Bill bill) {
		System.out.println("creating bill");
		return bill;
	}

	@GetMapping("/{id}")
	public Bill getBillById(@PathVariable int id) {
		System.out.println("fetching by id");
		return null;
	}
	
	@PutMapping
	public void updatenill(@PathVariable int id) {
		System.out.println("Updating bill with id:"+ id);
		
	}
	
	@DeleteMapping
	public void deletebill(@PathVariable int id) {
		System.out.println("deleting bill with id" +id);
	}

}


