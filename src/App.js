import { useEffect, useState } from "react";
import "./App.css";

const BASE_URL = "http://localhost:8080/api/contacts";

function App() {

  const [contacts, setContacts] = useState([]);

  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    phone: "",
    company: "",
    address: "",
  });

  const [editingId, setEditingId] = useState(null);

  // ================= GET ALL =================
  const fetchContacts = async () => {

    const response = await fetch(BASE_URL);

    const data = await response.json();

    setContacts(data);
  };

  useEffect(() => {
    fetchContacts();
  }, []);

  // ================= HANDLE INPUT =================
  const handleChange = (e) => {

    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  // ================= ADD / UPDATE =================
  const handleSubmit = async (e) => {

    e.preventDefault();

    // UPDATE
    if (editingId) {

      await fetch(`${BASE_URL}/${editingId}`, {

        method: "PUT",

        headers: {
          "Content-Type": "application/json",
        },

        body: JSON.stringify(formData),
      });

      setEditingId(null);

    } else {

      // CREATE
      await fetch(BASE_URL, {

        method: "POST",

        headers: {
          "Content-Type": "application/json",
        },

        body: JSON.stringify(formData),
      });
    }

    // RESET FORM
    setFormData({
      firstName: "",
      lastName: "",
      email: "",
      phone: "",
      company: "",
      address: "",
    });

    fetchContacts();
  };

  // ================= DELETE =================
  const deleteContact = async (id) => {

    await fetch(`${BASE_URL}/${id}`, {

      method: "DELETE",
    });

    fetchContacts();
  };

  // ================= GET BY ID =================
  const editContact = async (id) => {

    const response = await fetch(`${BASE_URL}/${id}`);

    const data = await response.json();

    setFormData({
      firstName: data.firstName,
      lastName: data.lastName,
      email: data.email,
      phone: data.phone,
      company: data.company,
      address: data.address,
    });

    setEditingId(id);
  };

  return (

    <div className="container">

      <h1>Contact Manager</h1>

      {/* ================= FORM ================= */}

      <form onSubmit={handleSubmit} className="form">

        <input
          type="text"
          name="firstName"
          placeholder="First Name"
          value={formData.firstName}
          onChange={handleChange}
          required
        />

        <input
          type="text"
          name="lastName"
          placeholder="Last Name"
          value={formData.lastName}
          onChange={handleChange}
          required
        />

        <input
          type="email"
          name="email"
          placeholder="Email"
          value={formData.email}
          onChange={handleChange}
          required
        />

        <input
          type="text"
          name="phone"
          placeholder="Phone"
          value={formData.phone}
          onChange={handleChange}
        />

        <input
          type="text"
          name="company"
          placeholder="Company"
          value={formData.company}
          onChange={handleChange}
        />

        <input
          type="text"
          name="address"
          placeholder="Address"
          value={formData.address}
          onChange={handleChange}
        />

        <button type="submit">

          {editingId ? "Update Contact" : "Add Contact"}

        </button>

      </form>

      {/* ================= CONTACT LIST ================= */}

      <div className="contact-list">

        {contacts.map((contact) => (

          <div className="card" key={contact.id}>

            <h2>
              {contact.firstName} {contact.lastName}
            </h2>

            <p>
              <strong>Email:</strong> {contact.email}
            </p>

            <p>
              <strong>Phone:</strong> {contact.phone}
            </p>

            <p>
              <strong>Company:</strong> {contact.company}
            </p>

            <p>
              <strong>Address:</strong> {contact.address}
            </p>

            <div className="btn-group">

              <button
                className="edit-btn"
                onClick={() => editContact(contact.id)}
              >
                Edit
              </button>

              <button
                className="delete-btn"
                onClick={() => deleteContact(contact.id)}
              >
                Delete
              </button>

            </div>

          </div>

        ))}

      </div>

    </div>
  );
}

export default App;