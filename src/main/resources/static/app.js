/**
 * Base endpoint URL for student CRUD operations.
 * @type {string}
 */
const API_URL = "http://localhost:8080/api/students";

/**
 * Base endpoint URL for authentication operations.
 * @type {string}
 */
const API_AUTH = "http://localhost:8080/api/auth";

/**
 * Sends a POST request to create a new student record.
 *
 * @async
 * @param {Object} studentData - The student details to save.
 * @param {string} studentData.firstName - The student's first name.
 * @param {string} studentData.lastName - The student's last name.
 * @param {string} studentData.email - The student's email address.
 * @returns {Promise<void>}
 */
async function createStudent(studentData) {
  try {
    const response = await fetch(API_URL, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify(studentData)
    });

    if (response.ok) {
      alert("Student added successfully!");
      resetForm();
      getAllStudents();
    } else {
      alert("Error adding student.");
    }
  } catch (error) {
    console.error("Error creating student:", error);
  }
}

/**
 * Resets the student form inputs, restores the submit button to its
 * default add state, and clears validation styles if available.
 *
 * @returns {void}
 */
function resetForm() {
  document.getElementById("studentId").value = "";
  document.getElementById("studentForm").reset();

  const submitBtn = document.getElementById("submitBtn");
  submitBtn.textContent = "Add Student";
  submitBtn.style.backgroundColor = "#28a745";

  if (typeof clearValidationStyles === "function") {
    clearValidationStyles();
  }
}

/**
 * Handles student form submission by validating inputs and triggering
 * either student creation or update depending on the presence of an ID.
 */
document.getElementById("studentForm").addEventListener("submit", function (e) {
  e.preventDefault();

  if (typeof validateStudentForm === "function" && !validateStudentForm()) {
    return;
  }

  const id = document.getElementById("studentId").value;
  const studentData = {
    firstName: document.getElementById("firstName").value.trim(),
    lastName: document.getElementById("lastName").value.trim(),
    email: document.getElementById("email").value.trim()
  };

  if (id) {
    updateStudent(id, studentData);
  } else {
    createStudent(studentData);
  }
});

/**
 * Fetches all student records from the backend and populates the table body.
 *
 * @async
 * @returns {Promise<void>}
 */
async function getAllStudents() {
  try {
    const response = await fetch(API_URL, {
      credentials: "include"
    });
    if (!response.ok) {
      throw new Error("Network response was not ok");
    }

    const students = await response.json();
    const tableBody = document.getElementById("studentTableBody");
    tableBody.innerHTML = "";

    students.forEach((student) => {
      const row = `
        <tr>
          <td>${student.id}</td>
          <td>${student.firstName}</td>
          <td>${student.lastName}</td>
          <td>${student.email}</td>
          <td>
            <button class="btn-edit" 
            onclick="initializeStudentFormById(${student.id})">Edit</button>
            <button class="btn-delete" 
            onclick="deleteStudent(${student.id})">Delete</button>
          </td>
        </tr>
      `;
      tableBody.innerHTML += row;
    });
  } catch (error) {
    console.error("Error fetching students:", error);
    alert("Failed to fetch students." + 
        "Are you logged in and is your Spring Boot backend running?");
  }
}

/**
 * Loads the student list once the DOM content has fully loaded.
 */
document.addEventListener("DOMContentLoaded", getAllStudents);

/**
 * Retrieves a single student's details by ID and populates the form
 * for editing.
 *
 * @async
 * @param {number|string} id - The unique identifier of the student.
 * @returns {Promise<void>}
 */
async function initializeStudentFormById(id) {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      credentials: "include"
    });
    if (!response.ok) {
      throw new Error("Could not fetch student details");
    }

    const student = await response.json();

    document.getElementById("studentId").value = student.id;
    document.getElementById("firstName").value = student.firstName;
    document.getElementById("lastName").value = student.lastName;
    document.getElementById("email").value = student.email;

    const submitBtn = document.getElementById("submitBtn");
    submitBtn.textContent = "Update";
    submitBtn.style.backgroundColor = "#007bff";

    if (typeof clearValidationStyles === "function") {
      clearValidationStyles();
    }
  } catch (error) {
    console.error("Error fetching student details:", error);
  }
}

/**
 * Sends a PUT request to update an existing student record.
 *
 * @async
 * @param {number|string} id - The unique identifier of the student to update.
 * @param {Object} studentData - The updated student details.
 * @param {string} studentData.firstName - The updated first name.
 * @param {string} studentData.lastName - The updated last name.
 * @param {string} studentData.email - The updated email address.
 * @returns {Promise<void>}
 */
async function updateStudent(id, studentData) {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify(studentData)
    });

    if (response.ok) {
      alert("Student updated successfully!");
      resetForm();
      getAllStudents();
    } else {
      alert("Error updating student.");
    }
  } catch (error) {
    console.error("Error updating student:", error);
  }
}

/**
 * Prompts for confirmation and sends a 
 * DELETE request to remove a student record.
 *
 * @async
 * @param {number|string} id - The unique identifier of the student to delete.
 * @returns {Promise<void>}
 */
async function deleteStudent(id) {
  if (confirm("Are you sure you want to delete this student?")) {
    try {
      const response = await fetch(`${API_URL}/${id}`, {
        method: "DELETE",
        credentials: "include"
      });

      if (response.ok) {
        const message = await response.text();
        alert(message);
        getAllStudents();
      } else {
        alert("Error deleting student.");
      }
    } catch (error) {
      console.error("Error deleting student:", error);
    }
  }
}

/**
 * Authenticates the user via session cookie 
 * and redirects to the main page on success.
 *
 * @async
 * @param {Object} loginDto - The user's login credentials.
 * @param {string} loginDto.username - The username.
 * @param {string} loginDto.password - The password.
 * @returns {Promise<void>}
 */
async function loginUser(loginDto) {
  try {
    const response = await fetch(`${API_AUTH}/auth_login_session`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(loginDto),
      credentials: "include"
    });

    if (response.ok) {
      alert("Login successful!");
      window.location.href = "index.html";
    } else {
      alert("Invalid credentials.");
    }
  } catch (error) {
    console.error("Login error:", error);
  }
}

/**
 * Logs the user out of the active session, clears the UI and browser credential
 * cache, and reloads the page.
 *
 * @async
 * @returns {Promise<void>}
 */
async function logoutUser() {
  try {
    await fetch(`${API_AUTH}/logout`, {
      method: "POST",
      credentials: "include"
    });
    document.getElementById("studentTableBody").innerHTML = "";
    resetForm();
    alert("Logged out successfully!");

    try {
      await fetch(API_URL, {
        method: "GET",
        headers: {
          "Authorization": "Basic " + btoa("invalid_user:wrong_password")
        }
      });
    } catch (err) {
      console.log("Browser credential cache cleared.");
    }
    window.location.reload();
  } catch (e) {
    console.error("Logout transmission crash:", e);
  }
}