import React, { useState, useEffect } from 'react';
import { useAuth } from '../auth/AuthContext';
import patientService from '../services/patientService';
import doctorService from '../services/doctorService';
import '../styles/Dashboard.css';

const AdminDashboard = () => {
  const { user, logout } = useAuth();
  const [patients, setPatients] = useState([]);
  const [doctors, setDoctors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [activeTab, setActiveTab] = useState('patients');
  
  // Patient form state
  const [showPatientForm, setShowPatientForm] = useState(false);
  const [editingPatient, setEditingPatient] = useState(null);
  const [patientForm, setPatientForm] = useState({
    name: '',
    age: '',
    gender: '',
    contact: '',
    address: ''
  });

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    setLoading(true);
    setError('');
    try {
      const [patientsData, doctorsData] = await Promise.all([
        patientService.getAllPatients(),
        doctorService.getAllDoctors()
      ]);
      setPatients(patientsData);
      setDoctors(doctorsData);
    } catch (err) {
      setError('Failed to fetch data: ' + (err.response?.data?.message || err.message));
      console.error('Error fetching data:', err);
    } finally {
      setLoading(false);
    }
  };

  const handlePatientFormChange = (e) => {
    setPatientForm({ ...patientForm, [e.target.name]: e.target.value });
  };

  const handleCreatePatient = async (e) => {
    e.preventDefault();
    try {
      if (editingPatient) {
        await patientService.updatePatient(editingPatient.id, patientForm);
      } else {
        await patientService.createPatient(patientForm);
      }
      setShowPatientForm(false);
      setEditingPatient(null);
      setPatientForm({ name: '', age: '', gender: '', contact: '', address: '' });
      fetchData();
    } catch (err) {
      setError('Failed to save patient: ' + (err.response?.data?.message || err.message));
    }
  };

  const handleEditPatient = (patient) => {
    setEditingPatient(patient);
    setPatientForm({
      name: patient.name,
      age: patient.age,
      gender: patient.gender,
      contact: patient.contact,
      address: patient.address
    });
    setShowPatientForm(true);
  };

  const handleDeletePatient = async (id) => {
    if (window.confirm('Are you sure you want to delete this patient?')) {
      try {
        await patientService.deletePatient(id);
        fetchData();
      } catch (err) {
        setError('Failed to delete patient: ' + (err.response?.data?.message || err.message));
      }
    }
  };

  return (
    <div className="dashboard">
      <nav className="dashboard-nav">
        <div className="nav-brand">HMS Admin</div>
        <div className="nav-user">
          <span>Welcome, {user.username}</span>
          <button onClick={logout} className="btn-logout">Logout</button>
        </div>
      </nav>

      <div className="dashboard-content">
        <h1>Admin Dashboard</h1>
        
        {error && <div className="error-message">{error}</div>}
        
        <div className="dashboard-tabs">
          <button 
            className={activeTab === 'patients' ? 'tab active' : 'tab'}
            onClick={() => setActiveTab('patients')}
          >
            Patients ({patients.length})
          </button>
          <button 
            className={activeTab === 'doctors' ? 'tab active' : 'tab'}
            onClick={() => setActiveTab('doctors')}
          >
            Doctors ({doctors.length})
          </button>
          <button 
            className={activeTab === 'appointments' ? 'tab active' : 'tab'}
            onClick={() => setActiveTab('appointments')}
          >
            Appointments (Coming Soon)
          </button>
        </div>

        {loading ? (
          <div className="loading">Loading...</div>
        ) : (
          <>
            {activeTab === 'patients' && (
              <div className="tab-content">
                <div className="section-header">
                  <h2>Patients Management</h2>
                  <button 
                    className="btn-primary"
                    onClick={() => {
                      setShowPatientForm(true);
                      setEditingPatient(null);
                      setPatientForm({ name: '', age: '', gender: '', contact: '', address: '' });
                    }}
                  >
                    Add New Patient
                  </button>
                </div>

                {showPatientForm && (
                  <div className="modal">
                    <div className="modal-content">
                      <h3>{editingPatient ? 'Edit Patient' : 'Add New Patient'}</h3>
                      <form onSubmit={handleCreatePatient}>
                        <div className="form-group">
                          <label>Name</label>
                          <input
                            type="text"
                            name="name"
                            value={patientForm.name}
                            onChange={handlePatientFormChange}
                            required
                          />
                        </div>
                        <div className="form-group">
                          <label>Age</label>
                          <input
                            type="number"
                            name="age"
                            value={patientForm.age}
                            onChange={handlePatientFormChange}
                            required
                          />
                        </div>
                        <div className="form-group">
                          <label>Gender</label>
                          <select
                            name="gender"
                            value={patientForm.gender}
                            onChange={handlePatientFormChange}
                            required
                          >
                            <option value="">Select</option>
                            <option value="MALE">Male</option>
                            <option value="FEMALE">Female</option>
                            <option value="OTHER">Other</option>
                          </select>
                        </div>
                        <div className="form-group">
                          <label>Contact</label>
                          <input
                            type="text"
                            name="contact"
                            value={patientForm.contact}
                            onChange={handlePatientFormChange}
                            required
                          />
                        </div>
                        <div className="form-group">
                          <label>Address</label>
                          <textarea
                            name="address"
                            value={patientForm.address}
                            onChange={handlePatientFormChange}
                            required
                          />
                        </div>
                        <div className="form-actions">
                          <button type="submit" className="btn-primary">
                            {editingPatient ? 'Update' : 'Create'}
                          </button>
                          <button 
                            type="button" 
                            className="btn-secondary"
                            onClick={() => {
                              setShowPatientForm(false);
                              setEditingPatient(null);
                            }}
                          >
                            Cancel
                          </button>
                        </div>
                      </form>
                    </div>
                  </div>
                )}

                <table className="data-table">
                  <thead>
                    <tr>
                      <th>ID</th>
                      <th>Name</th>
                      <th>Age</th>
                      <th>Gender</th>
                      <th>Contact</th>
                      <th>Address</th>
                      <th>Actions</th>
                    </tr>
                  </thead>
                  <tbody>
                    {patients.map((patient) => (
                      <tr key={patient.id}>
                        <td>{patient.id}</td>
                        <td>{patient.name}</td>
                        <td>{patient.age}</td>
                        <td>{patient.gender}</td>
                        <td>{patient.contact}</td>
                        <td>{patient.address}</td>
                        <td>
                          <button 
                            className="btn-edit"
                            onClick={() => handleEditPatient(patient)}
                          >
                            Edit
                          </button>
                          <button 
                            className="btn-delete"
                            onClick={() => handleDeletePatient(patient.id)}
                          >
                            Delete
                          </button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}

            {activeTab === 'doctors' && (
              <div className="tab-content">
                <h2>Doctors List</h2>
                <table className="data-table">
                  <thead>
                    <tr>
                      <th>ID</th>
                      <th>Name</th>
                      <th>Specialization</th>
                      <th>Contact</th>
                      <th>Email</th>
                    </tr>
                  </thead>
                  <tbody>
                    {doctors.map((doctor) => (
                      <tr key={doctor.id}>
                        <td>{doctor.id}</td>
                        <td>{doctor.name}</td>
                        <td>{doctor.specialization}</td>
                        <td>{doctor.contact}</td>
                        <td>{doctor.email}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}

            {activeTab === 'appointments' && (
              <div className="tab-content">
                <div className="placeholder-section">
                  <h2>Appointments Management</h2>
                  <p>🚧 Appointment feature coming soon!</p>
                  <p>This section will display:</p>
                  <ul>
                    <li>All scheduled appointments</li>
                    <li>Create new appointments</li>
                    <li>Assign doctors to patients</li>
                    <li>Manage appointment status</li>
                  </ul>
                  {/* TODO: Integrate appointments API when backend is ready
                      Expected endpoints:
                      - GET /api/v1/appointments
                      - POST /api/v1/appointments
                      - PUT /api/v1/appointments/{id}
                      - DELETE /api/v1/appointments/{id}
                  */}
                </div>
              </div>
            )}
          </>
        )}
      </div>
    </div>
  );
};

export default AdminDashboard;