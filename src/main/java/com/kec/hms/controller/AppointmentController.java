package com.kec.hms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kec.hms.model.Appointment;
import com.kec.hms.service.AppointmentService;


@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {
	
	@Autowired
	private AppointmentService appservice;
	
	@GetMapping
	public List<Appointment> getallAppointment(){
		System.out.println("fetching the appointments");
		return appservice.getAllAppointments();
	}
	
	@PostMapping
	public Appointment createAppointment(@RequestBody Appointment appointment) {
		System.out.println("creating appointment");
		
//		//prepare webhook payload
//		Map<String, Object> payload=new HashMap<>();
//		payload.put("appointmentID", appointment.getId());
//		payload.put("appointmentID", appointment.getPatientid());
//		payload.put("appointmentID", appointment.getDoctorId());
//		payload.put("appointmentID", appointment.getDate());
//		
//		
//		String webhookUrl="http://localhost:8801/webhook";
//		webhookService.sendWebhook(webhookUrl, payload);
		return appservice.addAppointment(appointment);
	}

	@GetMapping("/{id}")
	public Appointment getAppointmentById(@PathVariable int id) {
		System.out.println("fetching by id");
		return appservice.getAppointmentById(id);
	}
	
	@PutMapping("/{id}")
	public void updateAppointment(@PathVariable int id, @RequestBody Appointment appointment) {
		System.out.println("Updating appointment with id:"+ id);
		appservice.updateAppointment(id, appointment);
		
	}
	
	@DeleteMapping("/{id}")
	public void deleteAppointment(@PathVariable int id) {
		System.out.println("deletign appointment with id" +id);
		appservice.deleteAppointment(id);
	}

}
