// src/pages/DoctorDashboard.jsx
import React, { useState, useEffect } from 'react';
import { useAuth } from '../auth/AuthContext';
import doctorService from '../services/doctorService';
import appointmentService from '../services/appointmentService';
import patientService from '../services/patientService';
import '../styles/Dashboard.css';

const DoctorDashboard = () => {
  const { user, logout } = useAuth();
  const [activeTab, setActiveTab] = useState('profile');
  const [profile, setProfile] = useState(null);
  const [appointments, setAppointments] = useState([]);
  const [patients, setPatients] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [editing, setEditing] = useState(false);
  const [saving, setSaving] = useState(false);
  
  const [formData, setFormData] = useState({
    name: '',
    specialization: '',
    email: ''
  });

  useEffect(() => {
    fetchProfile();
    fetchAppointments();
    fetchPatients();
  }, []);

  const fetchProfile = async () => {
    setLoading(true);
    try {
      const data = await doctorService.getMyProfile();
      setProfile(data);
      
      if (data && data.name) {
        setFormData({
          name: data.name || '',
          specialization: data.specialization || '',
          email: data.email || ''
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
      const data = await appointmentService.getMyDoctorAppointments();
      setAppointments(data);
    } catch (err) {
      console.error('Failed to load appointments:', err);
    }
  };

  const fetchPatients = async () => {
    try {
      const data = await patientService.getAllPatients();
      setPatients(data);
    } catch (err) {
      console.error('Failed to load patients:', err);
    }
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSaving(true);
    setError('');
    
    try {
      await doctorService.updateMyProfile(formData);
      setEditing(false);
      await fetchProfile();
      alert('Profile saved successfully!');
    } catch (err) {
      setError('Failed to save profile: ' + err.message);
    } finally {
      setSaving(false);
    }
  };

  const handleCompleteAppointment = async (id) => {
    if (window.confirm('Mark this appointment as completed?')) {
      try {
        await appointmentService.completeAppointment(id);
        fetchAppointments();
        alert('Appointment marked as completed');
      } catch (err) {
        alert('Failed to update appointment: ' + err.message);
      }
    }
  };

  return (
    <div className="dashboard">
      <nav className="dashboard-nav">
        <div className="nav-brand">HMS Doctor Portal</div>
        <div className="nav-user">
          <span>Dr. {user.username}</span>
          <button onClick={logout} className="btn-logout">Logout</button>
        </div>
      </nav>

      <div className="dashboard-content">
        <h1>Doctor Dashboard</h1>
        
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
          <button 
            className={activeTab === 'patients' ? 'tab active' : 'tab'}
            onClick={() => setActiveTab('patients')}
          >
            Patients ({patients.length})
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
                      Please complete your profile to access all features.
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
                        <label>Specialization *</label>
                        <input
                          type="text"
                          name="specialization"
                          value={formData.specialization}
                          onChange={handleChange}
                          required
                          placeholder="e.g., Cardiology, Neurology"
                          disabled={saving}
                        />
                      </div>

                      <div className="form-group">
                        <label>Email *</label>
                        <input
                          type="email"
                          name="email"
                          value={formData.email}
                          onChange={handleChange}
                          required
                          placeholder="doctor@hospital.com"
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
                            onClick={() => {
                              setEditing(false);
                              setFormData({
                                name: profile.name,
                                specialization: profile.specialization,
                                email: profile.email
                              });
                            }}
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
                      <p><strong>Specialization:</strong> {profile?.specialization}</p>
                      <p><strong>Email:</strong> {profile?.email}</p>
                    </div>
                  )}
                </div>
              </div>
            )}

            {activeTab === 'appointments' && (
              <div className="tab-content">
                <h2>My Appointments</h2>
                {appointments.length === 0 ? (
                  <p className="info-note">No appointments scheduled yet.</p>
                ) : (
                  <table className="data-table">
                    <thead>
                      <tr>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Patient</th>
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
                          <td>{apt.patientName}</td>
                          <td>{apt.reason}</td>
                          <td>
                            <span className={`status-badge status-${apt.status.toLowerCase()}`}>
                              {apt.status}
                            </span>
                          </td>
                          <td>
                            {apt.status === 'SCHEDULED' && (
                              <button 
                                className="btn-edit"
                                onClick={() => handleCompleteAppointment(apt.id)}
                              >
                                Complete
                              </button>
                            )}
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                )}
              </div>
            )}

            {activeTab === 'patients' && (
              <div className="tab-content">
                <h2>Patient List</h2>
                {patients.length === 0 ? (
                  <p className="info-note">No patients found.</p>
                ) : (
                  <table className="data-table">
                    <thead>
                      <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Age</th>
                        <th>Gender</th>
                        <th>Phone</th>
                        <th>Address</th>
                      </tr>
                    </thead>
                    <tbody>
                      {patients.map((patient) => (
                        <tr key={patient.id}>
                          <td>{patient.id}</td>
                          <td>{patient.name}</td>
                          <td>{patient.age}</td>
                          <td>{patient.gender}</td>
                          <td>{patient.phone}</td>
                          <td>{patient.address}</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                )}
              </div>
            )}
          </>
        )}
      </div>
    </div>
  );
};

export default DoctorDashboard;