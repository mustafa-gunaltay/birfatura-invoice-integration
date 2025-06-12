import React, { useState } from "react";

function App() {
  const [formData, setFormData] = useState({
    currencyCode: "",
    invoiceTypeCode: "",
    noteText: "",
    supplierVkn: "",
    supplierName: "",
    supplierStreet: "",
    supplierBuildingNumber: "",
    supplierSubdivisionName: "",
    supplierCity: "",
    supplierPostalCode: "",
    supplierEmail: "",
    customerTckn: "",
    customerFirstName: "",
    customerLastName: "",
    customerStreet: "",
    customerBuildingNumber: "",
    customerSubdivisionName: "",
    customerCity: "",
    customerPostalCode: "",
    itemName: "",
    quantity: "",
    unitPrice: "",
    taxPercent: 18,
  });
  
  const [pdfLink, setPdfLink] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  // Field labels for better user experience
  const fieldLabels = {
    currencyCode: "Currency Code",
    invoiceTypeCode: "Invoice Type",
    noteText: "Note",
    supplierVkn: "Supplier Tax Number",
    supplierName: "Supplier Name",
    supplierStreet: "Supplier Street",
    supplierBuildingNumber: "Supplier Building No",
    supplierSubdivisionName: "Supplier District",
    supplierCity: "Supplier City",
    supplierPostalCode: "Supplier Postal Code",
    supplierEmail: "Supplier Email",
    customerTckn: "Customer ID Number",
    customerFirstName: "Customer First Name",
    customerLastName: "Customer Last Name",
    customerStreet: "Customer Street",
    customerBuildingNumber: "Customer Building No",
    customerSubdivisionName: "Customer District",
    customerCity: "Customer City",
    customerPostalCode: "Customer Postal Code",
    itemName: "Product Name",
    quantity: "Quantity",
    unitPrice: "Unit Price (₺)",
    taxPercent: "Tax Rate (%)",
  };

  // Group fields by category
  const fieldGroups = {
    "Invoice Details": ["currencyCode", "invoiceTypeCode", "noteText"],
    "Supplier Information": [
      "supplierVkn", "supplierName", "supplierEmail",
      "supplierStreet", "supplierBuildingNumber", "supplierSubdivisionName",
      "supplierCity", "supplierPostalCode"
    ],
    "Customer Information": [
      "customerTckn", "customerFirstName", "customerLastName",
      "customerStreet", "customerBuildingNumber", "customerSubdivisionName",
      "customerCity", "customerPostalCode"
    ],
    "Product Details": ["itemName", "quantity", "unitPrice", "taxPercent"]
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const isFormValid = () => {
    return Object.values(formData).every((val) => val !== "" && val !== null && val !== undefined);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setPdfLink(null);
    setLoading(true);

    if (!isFormValid()) {
      setError("Please fill in all fields.");
      setLoading(false);
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/api/v1/document/send-document", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      console.log("=== RESPONSE DEBUG ===");
      console.log("HTTP Status:", response.status);
      console.log("Response OK:", response.ok);
      
      const responseText = await response.text();
      console.log("Raw Response Text:", responseText);
      
      if (!responseText) {
        setError("Empty response received from server.");
        return;
      }
      
      let data;
      try {
        data = JSON.parse(responseText);
        console.log("Parsed JSON:", data);
      } catch (parseError) {
        console.error("JSON Parse Error:", parseError);
        setError("Invalid response format from server.");
        return;
      }
      
      console.log("=== DATA DEBUG ===");
      console.log("data.Success:", data.Success);
      console.log("data.Message:", data.Message);
      console.log("data.Result:", data.Result);
      if (data.Result) {
        console.log("data.Result.pdfLink:", data.Result.pdfLink);
      }
      console.log("=== END DEBUG ===");

      if (data.Success) {
        if (data.Result && data.Result.pdfLink) {
          setPdfLink(data.Result.pdfLink);
          console.log("PDF Link set:", data.Result.pdfLink);
        } else {
          setError("PDF link not found in response.");
        }
      } else {
        setError(data.Message || "An error occurred while creating the invoice.");
      }
    } catch (err) {
      console.error("Fetch error:", err);
      setError("Connection error: " + err.message);
    } finally {
      setLoading(false);
    }
  };

  const styles = {
    container: {
      maxWidth: '1200px',
      margin: '0 auto',
      padding: '20px',
      fontFamily: '-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif',
      backgroundColor: '#f8f9fa',
      minHeight: '100vh'
    },
    header: {
      textAlign: 'center',
      marginBottom: '40px',
      padding: '20px',
      backgroundColor: '#fff',
      borderRadius: '12px',
      boxShadow: '0 2px 10px rgba(0,0,0,0.1)'
    },
    title: {
      color: '#2c3e50',
      fontSize: '32px',
      fontWeight: '700',
      margin: '0',
      background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
      WebkitBackgroundClip: 'text',
      WebkitTextFillColor: 'transparent',
      backgroundClip: 'text'
    },
    subtitle: {
      color: '#6c757d',
      fontSize: '16px',
      margin: '10px 0 0 0',
      fontWeight: '400'
    },
    form: {
      backgroundColor: '#fff',
      borderRadius: '12px',
      padding: '30px',
      boxShadow: '0 4px 20px rgba(0,0,0,0.1)',
      marginBottom: '20px'
    },
    sectionTitle: {
      fontSize: '20px',
      fontWeight: '600',
      color: '#2c3e50',
      marginBottom: '20px',
      marginTop: '30px',
      paddingBottom: '10px',
      borderBottom: '2px solid #e9ecef'
    },
    fieldsGrid: {
      display: 'grid',
      gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))',
      gap: '20px',
      marginBottom: '20px'
    },
    fieldGroup: {
      display: 'flex',
      flexDirection: 'column'
    },
    label: {
      fontSize: '14px',
      fontWeight: '600',
      color: '#495057',
      marginBottom: '8px'
    },
    input: {
      width: '100%',
      padding: '12px 16px',
      fontSize: '14px',
      border: '2px solid #e9ecef',
      borderRadius: '8px',
      outline: 'none',
      transition: 'all 0.3s ease',
      backgroundColor: '#fff',
      boxSizing: 'border-box'
    },
    inputFocus: {
      borderColor: '#667eea',
      boxShadow: '0 0 0 3px rgba(102, 126, 234, 0.1)'
    },
    inputDisabled: {
      backgroundColor: '#f8f9fa',
      cursor: 'not-allowed',
      opacity: '0.7'
    },
    submitButton: {
      width: '100%',
      padding: '16px',
      fontSize: '16px',
      fontWeight: '600',
      color: '#fff',
      background: loading ? '#6c757d' : 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
      border: 'none',
      borderRadius: '8px',
      cursor: loading ? 'not-allowed' : 'pointer',
      transition: 'all 0.3s ease',
      marginTop: '20px',
      textTransform: 'uppercase',
      letterSpacing: '1px'
    },
    errorMessage: {
      backgroundColor: '#f8d7da',
      color: '#721c24',
      padding: '12px 16px',
      borderRadius: '8px',
      margin: '20px 0',
      border: '1px solid #f5c6cb',
      fontSize: '14px',
      fontWeight: '500'
    },
    successMessage: {
      backgroundColor: '#d1edff',
      border: '1px solid #bee5eb',
      borderRadius: '12px',
      padding: '20px',
      marginTop: '20px',
      textAlign: 'center'
    },
    successTitle: {
      color: '#0c5460',
      fontSize: '18px',
      fontWeight: '600',
      margin: '0 0 10px 0'
    },
    pdfLink: {
      display: 'inline-block',
      backgroundColor: '#667eea',
      color: '#fff',
      padding: '12px 24px',
      borderRadius: '8px',
      textDecoration: 'none',
      fontWeight: '600',
      transition: 'all 0.3s ease',
      boxShadow: '0 2px 10px rgba(102, 126, 234, 0.3)'
    }
  };

  
  return (
    <div style={styles.container}>
      <div style={styles.header}>
        <h1 style={styles.title}>BirFatura Invoice Generator</h1>
        <p style={styles.subtitle}>Create professional invoices with ease</p>
      </div>

      <form onSubmit={handleSubmit} style={styles.form}>
        {Object.entries(fieldGroups).map(([sectionName, fields]) => (
          <div key={sectionName}>
            <h2 style={styles.sectionTitle}>
              {sectionName}
            </h2>
            <div style={styles.fieldsGrid}>
              {fields.map((fieldName) => (
                <div key={fieldName} style={styles.fieldGroup}>
                  <label style={styles.label}>
                    {fieldLabels[fieldName]}
                    {fieldName === 'taxPercent' && <span style={{color: '#28a745', marginLeft: '5px'}}>*</span>}
                  </label>
                  <input
                    type={fieldName === "taxPercent" || fieldName === "quantity" ? "number" : 
                          fieldName.includes("Email") ? "email" : "text"}
                    name={fieldName}
                    value={formData[fieldName]}
                    onChange={handleChange}
                    style={{
                      ...styles.input,
                      ...(loading ? styles.inputDisabled : {})
                    }}
                    required
                    disabled={loading}
                    min={fieldName === "taxPercent" ? 0 : fieldName === "quantity" ? 1 : undefined}
                    max={fieldName === "taxPercent" ? 100 : undefined}
                    step={fieldName === "unitPrice" ? "0.01" : undefined}
                    placeholder={
                      fieldName === "currencyCode" ? "e.g., TRY, USD, EUR" :
                      fieldName === "invoiceTypeCode" ? "e.g., SATIS, IADE" :
                      fieldName === "supplierVkn" ? "10-digit tax number" :
                      fieldName === "customerTckn" ? "11-digit ID number" :
                      fieldName === "taxPercent" ? "18" :
                      `Enter ${fieldLabels[fieldName].toLowerCase()}`
                    }
                  />
                </div>
              ))}
            </div>
          </div>
        ))}

        <button 
          type="submit" 
          style={styles.submitButton}
          disabled={loading}
          onMouseOver={(e) => !loading && (e.target.style.transform = 'translateY(-2px)')}
          onMouseOut={(e) => (e.target.style.transform = 'translateY(0)')}
        >
          {loading ? "Processing..." : "Generate Invoice"}
        </button>
      </form>
      
      {error && (
        <div style={styles.errorMessage}>
          <strong>Error:</strong> {error}
        </div>
      )}
      
      {pdfLink && (
        <div style={styles.successMessage}>
          <h3 style={styles.successTitle}>
            Invoice Generated Successfully!
          </h3>
          <p style={{color: '#0c5460', margin: '10px 0'}}>
            Your invoice has been created and is ready for download.
          </p>
          <a 
            href={pdfLink} 
            target="_blank" 
            rel="noopener noreferrer"
            style={styles.pdfLink}
            onMouseOver={(e) => {
              e.target.style.backgroundColor = '#5a6fd8';
              e.target.style.transform = 'translateY(-2px)';
            }}
            onMouseOut={(e) => {
              e.target.style.backgroundColor = '#667eea';
              e.target.style.transform = 'translateY(0)';
            }}
          >
            Download PDF Invoice
          </a>
        </div>
      )}
    </div>
  );
}

export default App;