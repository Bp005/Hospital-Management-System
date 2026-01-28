// src/pages/PatientDashboard.jsx - WITH APPOINTMENTS
import React, { useState, useEffect } from 'react';
import { useAuth } from '../auth/AuthContext';
import patientService from '../services/patientService';
import appointmentService from '../services/appointmentService';
import doctorService from '../services/doctorService';
import '../styles/Dashboard.css';

const PatientDashboard = () => {
  const { user, logout } = useAuth();
  const [activeTab, setActiveTab] = useState('profile');
  const [profile, setProfile] = useState(null);
  const [appointments, setAppointments] = useState([]);
  const [doctors, setDoctors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [editing, setEditing] = useState(false);
  const [saving, setSaving] = useState(false);
  const [bookingAppointment, setBookingAppointment] = useState(false);
  
  const [formData, setFormData] = useState({
    name: '',
    age: '',
    gender: '',
    phone: '',
    address: '',
    dob: ''
  });

  const [appointmentForm, setAppointmentForm] = useState({
    doctorId: '',
    appointmentDate: '',
    appointmentTime: '',
    reason: '',
    notes: ''
  });

  useEffect(() => {
    fetchProfile();
    fetchAppointments();
    fetchDoctors();
  }, []);

  const fetchProfile = async () => {
    setLoading(true);
    try {
      const data = await patientService.getMyProfile();
      setProfile(data);
      
      if (data && data.name) {
        setFormData({
          name: data.name || '',
          age: data.age || '',
          gender: data.gender || '',
          phone: data.phone || '',
          address: data.address || '',
          dob: data.dob || ''
        });
        setEditing(false);
      } else {
        setEditing(true);
      }
    } catch (err) {
      setError('Failed to load profile: ' + err.message);
    } finally {
      setLoading(false);
    }
  };

  const fetchAppointments = async () => {
    try {
      const data = await appointmentService.getMyAppointments();
      setAppointments(data);
    } catch (err) {
      console.error('Failed to load appointments:', err);
    }
  };

  const fetchDoctors = async () => {
    try {
      const data = await doctorService.getAvailableDoctors();
      setDoctors(data);
    } catch (err) {
      console.error('Failed to load doctors:', err);
    }
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleAppointmentChange = (e) => {
    setAppointmentForm({ ...appointmentForm, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSaving(true);
    setError('');
    
    try {
      await patientService.updateMyProfile(formData);
      setEditing(false);
      await fetchProfile();
      alert('Profile saved successfully!');
    } catch (err) {
      setError('Failed to save profile: ' + err.message);
    } finally {
      setSaving(false);
    }
  };

  const handleBookAppointment = async (e) => {
    e.preventDefault();
    
    if (!profile || !profile.name) {
      alert('Please complete your profile first before booking appointments');
      setActiveTab('profile');
      return;
    }
    
    try {
      await appointmentService.bookAppointment(appointmentForm);
      setBookingAppointment(false);
      setAppointmentForm({
        doctorId: '',
        appointmentDate: '',
        appointmentTime: '',
        reason: '',
        notes: ''
      });
      await fetchAppointments();
      alert('Appointment booked successfully!');
    } catch (err) {
      alert('Failed to book appointment: ' + err.message);
    }
  };

  const handleCancelAppointment = async (id) => {
    if (window.confirm('Are you sure you want to cancel this appointment?')) {
      try {
        await appointmentService.cancelAppointment(id);
        await fetchAppointments();
        alert('Appointment cancelled');
      } catch (err) {
        alert('Failed to cancel appointment: ' + err.message);
      }
    }
  };

  return (
    <div className="dashboard">
      <nav className="dashboard-nav">
        <div className="nav-brand">HMS Patient Portal</div>
        <div className="nav-user">
          <span>Welcome, {user.username}</span>
          <button onClick={logout} className="btn-logout">Logout</button>
        </div>
      </nav>

      <div className="dashboard-content">
        <h1>Patient Dashboard</h1>
        
        {error && <div className="error-message">{error}</div>}
        
        <div className="dashboard-tabs">
          <button 
            className={activeTab === 'profile' ? 'tab active' : 'tab'}
            onClick={() => setActiveTab('profile')}
          >
            My Profile
          </button>
          <button 
            className={activeTab === 'appointments' ? 'tab active' : 'tab'}
            onClick={() => setActiveTab('appointments')}
          >
            My Appointments ({appointments.length})
          </button>
        </div>

        {loading ? (
          <div className="loading">Loading...</div>
        ) : (
          <>
            {activeTab === 'profile' && (
              <div className="tab-content">
                <div className="profile-section">
                  <div className="section-header">
                    <h2>My Profile</h2>
                    {!editing && profile && profile.name && (
                      <button className="btn-primary" onClick={() => setEditing(true)}>
                        Edit Profile
                      </button>
                    )}
                  </div>

                  {!profile || !profile.name ? (
                    <div className="info-note">
                      Please complete your profile to book appointments.
                    </div>
                  ) : null}

                  {editing ? (
                    <form onSubmit={handleSubmit} className="profile-form">
                      <div className="form-group">
                        <label>Full Name *</label>
                        <input
                          type="text"
                          name="name"
                          value={formData.name}
                          onChange={handleChange}
                          required
                          disabled={saving}
                        />
                      </div>

                      <div className="form-group">
                        <label>Date of Birth *</label>
                        <input
                          type="date"
                          name="dob"
                          value={formData.dob}
                          onChange={handleChange}
                          required
                          disabled={saving}
                        />
                      </div>

                      <div className="form-group">
                        <label>Age *</label>
                        <input
                          type="number"
                          name="age"
                          value={formData.age}
                          onChange={handleChange}
                          required
                          disabled={saving}
                        />
                      </div>

                      <div className="form-group">
                        <label>Gender *</label>
                        <select
                          name="gender"
                          value={formData.gender}
                          onChange={handleChange}
                          required
                          disabled={saving}
                        >
                          <option value="">Select Gender</option>
                          <option value="MALE">Male</option>
                          <option value="FEMALE">Female</option>
                          <option value="OTHER">Other</option>
                        </select>
                      </div>

                      <div className="form-group">
                        <label>Phone Number *</label>
                        <input
                          type="tel"
                          name="phone"
                          value={formData.phone}
                          onChange={handleChange}
                          required
                          disabled={saving}
                        />
                      </div>

                      <div className="form-group">
                        <label>Address *</label>
                        <textarea
                          name="address"
                          value={formData.address}
                          onChange={handleChange}
                          required
                          rows="3"
                          disabled={saving}
                        />
                      </div>

                      <div className="form-actions">
                        <button type="submit" className="btn-primary" disabled={saving}>
                          {saving ? 'Saving...' : 'Save Profile'}
                        </button>
                        {profile && profile.name && (
                          <button 
                            type="button" 
                            className="btn-secondary"
                            onClick={() => setEditing(false)}
                            disabled={saving}
                          >
                            Cancel
                          </button>
                        )}
                      </div>
                    </form>
                  ) : (
                    <div className="profile-info">
                      <p><strong>Username:</strong> {profile?.username}</p>
                      <p><strong>Role:</strong> {profile?.role}</p>
                      <p><strong>Name:</strong> {profile?.name}</p>
                      <p><strong>Date of Birth:</strong> {profile?.dob}</p>
                      <p><strong>Age:</strong> {profile?.age}</p>
                      <p><strong>Gender:</strong> {profile?.gender}</p>
                      <p><strong>Phone:</strong> {profile?.phone}</p>
                      <p><strong>Address:</strong> {profile?.address}</p>
                    </div>
                  )}
                </div>
              </div>
            )}

            {activeTab === 'appointments' && (
              <div className="tab-content">
                <div className="section-header">
                  <h2>My Appointments</h2>
                  {profile && profile.name && !bookingAppointment && (
                    <button 
                      className="btn-primary"
                      onClick={() => setBookingAppointment(true)}
                    >
                      Book New Appointment
                    </button>
                  )}
                </div>

                {!profile || !profile.name ? (
                  <div className="info-note">
                    Please complete your profile before booking appointments.
                  </div>
                ) : bookingAppointment ? (
                  <form onSubmit={handleBookAppointment} className="profile-form">
                    <h3>Book New Appointment</h3>
                    
                    <div className="form-group">
                      <label>Select Doctor *</label>
                      <select
                        name="doctorId"
                        value={appointmentForm.doctorId}
                        onChange={handleAppointmentChange}
                        required
                      >
                        <option value="">Choose a doctor</option>
                        {doctors.map(doctor => (
                          <option key={doctor.id} value={doctor.id}>
                            Dr. {doctor.name} - {doctor.specialization}
                          </option>
                        ))}
                      </select>
                    </div>

                    <div className="form-group">
                      <label>Appointment Date *</label>
                      <input
                        type="date"
                        name="appointmentDate"
                        value={appointmentForm.appointmentDate}
                        onChange={handleAppointmentChange}
                        min={new Date().toISOString().split('T')[0]}
                        required
                      />
                    </div>

                    <div className="form-group">
                      <label>Appointment Time *</label>
                      <input
                        type="time"
                        name="appointmentTime"
                        value={appointmentForm.appointmentTime}
                        onChange={handleAppointmentChange}
                        required
                      />
                    </div>

                    <div className="form-group">
                      <label>Reason for Visit *</label>
                      <textarea
                        name="reason"
                        value={appointmentForm.reason}
                        onChange={handleAppointmentChange}
                        required
                        rows="3"
                        placeholder="Describe your symptoms or reason for visit"
                      />
                    </div>

                    <div className="form-group">
                      <label>Additional Notes</label>
                      <textarea
                        name="notes"
                        value={appointmentForm.notes}
                        onChange={handleAppointmentChange}
                        rows="2"
                        placeholder="Any additional information (optional)"
                      />
                    </div>

                    <div className="form-actions">
                      <button type="submit" className="btn-primary">
                        Book Appointment
                      </button>
                      <button 
                        type="button"
                        className="btn-secondary"
                        onClick={() => setBookingAppointment(false)}
                      >
                        Cancel
                      </button>
                    </div>
                  </form>
                ) : (
                  <>
                    {appointments.length === 0 ? (
                      <p className="info-note">You have no appointments yet.</p>
                    ) : (
                      <table className="data-table">
                        <thead>
                          <tr>
                            <th>Date</th>
                            <th>Time</th>
                            <th>Doctor</th>
                            <th>Specialization</th>
                            <th>Reason</th>
                            <th>Status</th>
                            <th>Actions</th>
                          </tr>
                        </thead>
                        <tbody>
                          {appointments.map((apt) => (
                            <tr key={apt.id}>
                              <td>{apt.appointmentDate}</td>
                              <td>{apt.appointmentTime}</td>
                              <td>Dr. {apt.doctorName}</td>
                              <td>{apt.doctorSpecialization}</td>
                              <td>{apt.reason}</td>
                              <td>
                                <span className={`status-badge status-${apt.status.toLowerCase()}`}>
                                  {apt.status}
                                </span>
                              </td>
                              <td>
                                {apt.status === 'SCHEDULED' && (
                                  <button 
                                    className="btn-delete"
                                    onClick={() => handleCancelAppointment(apt.id)}
                                  >
                                    Cancel
                                  </button>
                                )}
                              </td>
                            </tr>
                          ))}
                        </tbody>
                      </table>
                    )}
                  </>
                )}
              </div>
            )}
          </>
        )}
      </div>
    </div>
  );
};

export default PatientDashboard;