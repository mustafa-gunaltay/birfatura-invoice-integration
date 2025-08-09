import React, { useState, useEffect } from "react";

// Simulated Router (since we can't import react-router-dom)
const Router = ({ children, currentPage, setCurrentPage }) => {
  return (
      <div>
        {React.Children.map(children, child =>
            React.cloneElement(child, { currentPage, setCurrentPage })
        )}
      </div>
  );
};

const Route = ({ path, component: Component, currentPage, setCurrentPage }) => {
  return currentPage === path ? <Component setCurrentPage={setCurrentPage} /> : null;
};

// Home Page Component
const HomePage = ({ setCurrentPage }) => {
  const modules = [
    {
      title: "Generate Invoice",
      description: "Create professional invoices with ease",
      path: "generate-invoice",
      icon: "📄",
      color: "linear-gradient(135deg, #667eea 0%, #764ba2 100%)"
    },
    {
      title: "Show Invoices",
      description: "View and manage all your invoices",
      path: "show-invoices",
      icon: "📋",
      color: "linear-gradient(135deg, #f093fb 0%, #f5576c 100%)"
    },
    {
      title: "Automatic Calculation",
      description: "Calculate tax and totals automatically",
      path: "automatic-calculation",
      icon: "🧮",
      color: "linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)"
    }
  ];

  const styles = {
    container: {
      minHeight: '100vh',
      background: 'linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%)',
      padding: '40px 20px',
      fontFamily: '-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif'
    },
    header: {
      textAlign: 'center',
      marginBottom: '60px'
    },
    title: {
      fontSize: '48px',
      fontWeight: '800',
      background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
      WebkitBackgroundClip: 'text',
      WebkitTextFillColor: 'transparent',
      marginBottom: '16px',
      textShadow: '0 4px 8px rgba(0,0,0,0.1)'
    },
    subtitle: {
      fontSize: '20px',
      color: '#6c757d',
      fontWeight: '400',
      maxWidth: '600px',
      margin: '0 auto',
      lineHeight: '1.6'
    },
    modulesGrid: {
      display: 'grid',
      gridTemplateColumns: 'repeat(auto-fit, minmax(350px, 1fr))',
      gap: '30px',
      maxWidth: '1200px',
      margin: '0 auto'
    },
    moduleCard: {
      background: '#fff',
      borderRadius: '20px',
      padding: '40px 30px',
      boxShadow: '0 10px 40px rgba(0,0,0,0.1)',
      cursor: 'pointer',
      transition: 'all 0.3s cubic-bezier(0.4, 0, 0.2, 1)',
      textAlign: 'center',
      position: 'relative',
      overflow: 'hidden'
    },
    moduleIcon: {
      fontSize: '48px',
      marginBottom: '20px',
      display: 'block'
    },
    moduleTitle: {
      fontSize: '24px',
      fontWeight: '700',
      color: '#2c3e50',
      marginBottom: '12px'
    },
    moduleDesc: {
      fontSize: '16px',
      color: '#6c757d',
      lineHeight: '1.5',
      marginBottom: '24px'
    },
    moduleButton: {
      padding: '12px 24px',
      borderRadius: '10px',
      border: 'none',
      color: '#fff',
      fontWeight: '600',
      fontSize: '14px',
      cursor: 'pointer',
      transition: 'all 0.3s ease',
      textTransform: 'uppercase',
      letterSpacing: '1px'
    }
  };

  return (
      <div style={styles.container}>
        <div style={styles.header}>
          <h1 style={styles.title}>BirFatura Invoice Generator</h1>
          <p style={styles.subtitle}>
            Comprehensive e-invoice management system with professional tools for modern businesses
          </p>
        </div>

        <div style={styles.modulesGrid}>
          {modules.map((module, index) => (
              <div
                  key={index}
                  style={styles.moduleCard}
                  onClick={() => setCurrentPage(module.path)}
                  onMouseOver={(e) => {
                    e.currentTarget.style.transform = 'translateY(-10px)';
                    e.currentTarget.style.boxShadow = '0 20px 60px rgba(0,0,0,0.15)';
                  }}
                  onMouseOut={(e) => {
                    e.currentTarget.style.transform = 'translateY(0)';
                    e.currentTarget.style.boxShadow = '0 10px 40px rgba(0,0,0,0.1)';
                  }}
              >
                <span style={styles.moduleIcon}>{module.icon}</span>
                <h3 style={styles.moduleTitle}>{module.title}</h3>
                <p style={styles.moduleDesc}>{module.description}</p>
                <button
                    style={{
                      ...styles.moduleButton,
                      background: module.color
                    }}
                    onMouseOver={(e) => {
                      e.target.style.transform = 'scale(1.05)';
                    }}
                    onMouseOut={(e) => {
                      e.target.style.transform = 'scale(1)';
                    }}
                >
                  Kısa modül tanıtımı
                </button>
              </div>
          ))}
        </div>
      </div>
  );
};

// Generate Invoice Page (your existing form)
const GenerateInvoicePage = ({ setCurrentPage }) => {
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

      const responseText = await response.text();

      if (!responseText) {
        setError("Empty response received from server.");
        return;
      }

      let data;
      try {
        data = JSON.parse(responseText);
      } catch (parseError) {
        setError("Invalid response format from server.");
        return;
      }

      if (data.Success) {
        if (data.Result && data.Result.pdfLink) {
          setPdfLink(data.Result.pdfLink);
        } else {
          setError("PDF link not found in response.");
        }
      } else {
        setError(data.Message || "An error occurred while creating the invoice.");
      }
    } catch (err) {
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
      fontFamily: '-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif',
      backgroundColor: '#f8f9fa',
      minHeight: '100vh'
    },
    backButton: {
      padding: '10px 20px',
      backgroundColor: '#6c757d',
      color: '#fff',
      border: 'none',
      borderRadius: '8px',
      cursor: 'pointer',
      marginBottom: '20px',
      fontSize: '14px',
      fontWeight: '600'
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
      WebkitTextFillColor: 'transparent'
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
    pdfLink: {
      display: 'inline-block',
      backgroundColor: '#667eea',
      color: '#fff',
      padding: '12px 24px',
      borderRadius: '8px',
      textDecoration: 'none',
      fontWeight: '600',
      transition: 'all 0.3s ease'
    }
  };

  return (
      <div style={styles.container}>
        <button
            style={styles.backButton}
            onClick={() => setCurrentPage('home')}
        >
          ← Ana Sayfaya Dön
        </button>

        <div style={styles.header}>
          <h1 style={styles.title}>Generate Invoice</h1>
        </div>

        <form onSubmit={handleSubmit} style={styles.form}>
          {Object.entries(fieldGroups).map(([sectionName, fields]) => (
              <div key={sectionName}>
                <h2 style={styles.sectionTitle}>{sectionName}</h2>
                <div style={styles.fieldsGrid}>
                  {fields.map((fieldName) => (
                      <div key={fieldName} style={styles.fieldGroup}>
                        <label style={styles.label}>{fieldLabels[fieldName]}</label>
                        <input
                            type={fieldName === "taxPercent" || fieldName === "quantity" ? "number" :
                                fieldName.includes("Email") ? "email" : "text"}
                            name={fieldName}
                            value={formData[fieldName]}
                            onChange={handleChange}
                            style={styles.input}
                            required
                            disabled={loading}
                            placeholder={
                              fieldName === "currencyCode" ? "e.g., TRY, USD, EUR" :
                                  fieldName === "invoiceTypeCode" ? "e.g., SATIS, IADE" :
                                      `Enter ${fieldLabels[fieldName].toLowerCase()}`
                            }
                        />
                      </div>
                  ))}
                </div>
              </div>
          ))}

          <button type="submit" style={styles.submitButton} disabled={loading}>
            {loading ? "Processing..." : "Generate Invoice"}
          </button>
        </form>

        {error && <div style={styles.errorMessage}><strong>Error:</strong> {error}</div>}

        {pdfLink && (
            <div style={styles.successMessage}>
              <h3>Invoice Generated Successfully!</h3>
              <a href={pdfLink} target="_blank" rel="noopener noreferrer" style={styles.pdfLink}>
                Download PDF Invoice
              </a>
            </div>
        )}
      </div>
  );
};

// Show Invoices Page
const ShowInvoicesPage = ({ setCurrentPage }) => {
  const [invoices, setInvoices] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchInvoices();
  }, []);

  const fetchInvoices = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/invoices');
      if (!response.ok) {
        throw new Error('Failed to fetch invoices');
      }
      const data = await response.json();
      setInvoices(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const formatDate = (dateString) => {
    if (!dateString) return 'N/A';
    return new Date(dateString).toLocaleDateString('tr-TR');
  };

  const styles = {
    container: {
      maxWidth: '1200px',
      margin: '0 auto',
      padding: '20px',
      fontFamily: '-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif',
      backgroundColor: '#f8f9fa',
      minHeight: '100vh'
    },
    backButton: {
      padding: '10px 20px',
      backgroundColor: '#6c757d',
      color: '#fff',
      border: 'none',
      borderRadius: '8px',
      cursor: 'pointer',
      marginBottom: '20px',
      fontSize: '14px',
      fontWeight: '600'
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
      background: 'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)',
      WebkitBackgroundClip: 'text',
      WebkitTextFillColor: 'transparent'
    },
    invoicesGrid: {
      display: 'grid',
      gridTemplateColumns: 'repeat(auto-fill, minmax(350px, 1fr))',
      gap: '20px'
    },
    invoiceCard: {
      backgroundColor: '#fff',
      borderRadius: '12px',
      padding: '20px',
      boxShadow: '0 4px 20px rgba(0,0,0,0.1)',
      transition: 'transform 0.3s ease'
    },
    invoiceHeader: {
      display: 'flex',
      justifyContent: 'space-between',
      alignItems: 'center',
      marginBottom: '15px',
      paddingBottom: '10px',
      borderBottom: '2px solid #e9ecef'
    },
    invoiceId: {
      fontSize: '12px',
      color: '#6c757d',
      fontWeight: '600'
    },
    invoiceDate: {
      fontSize: '14px',
      color: '#495057',
      fontWeight: '500'
    },
    invoiceDetails: {
      marginBottom: '15px'
    },
    detailRow: {
      display: 'flex',
      justifyContent: 'space-between',
      margin: '8px 0',
      fontSize: '14px'
    },
    label: {
      fontWeight: '600',
      color: '#495057'
    },
    value: {
      color: '#6c757d'
    },
    pdfButton: {
      width: '100%',
      padding: '10px',
      backgroundColor: '#f093fb',
      color: '#fff',
      border: 'none',
      borderRadius: '8px',
      cursor: 'pointer',
      fontWeight: '600',
      transition: 'background-color 0.3s ease'
    },
    loadingMessage: {
      textAlign: 'center',
      fontSize: '18px',
      color: '#6c757d',
      padding: '40px'
    },
    errorMessage: {
      backgroundColor: '#f8d7da',
      color: '#721c24',
      padding: '12px 16px',
      borderRadius: '8px',
      margin: '20px 0',
      border: '1px solid #f5c6cb'
    },
    emptyMessage: {
      textAlign: 'center',
      fontSize: '18px',
      color: '#6c757d',
      padding: '60px',
      backgroundColor: '#fff',
      borderRadius: '12px'
    }
  };

  return (
      <div style={styles.container}>
        <button
            style={styles.backButton}
            onClick={() => setCurrentPage('home')}
        >
          ← Ana Sayfaya Dön
        </button>

        <div style={styles.header}>
          <h1 style={styles.title}>Show Invoices</h1>
          <p>Tüm oluşturulan faturaların listesi</p>
        </div>

        {loading && <div style={styles.loadingMessage}>Faturalar yükleniyor...</div>}

        {error && (
            <div style={styles.errorMessage}>
              <strong>Hata:</strong> {error}
            </div>
        )}

        {!loading && !error && invoices.length === 0 && (
            <div style={styles.emptyMessage}>
              <h3>Henüz fatura bulunmuyor</h3>
              <p>İlk faturanızı oluşturmak için "Generate Invoice" sayfasını kullanın.</p>
            </div>
        )}

        {!loading && !error && invoices.length > 0 && (
            <div style={styles.invoicesGrid}>
              {invoices.map((invoice, index) => (
                  <div
                      key={invoice.uuid || index}
                      style={styles.invoiceCard}
                      onMouseOver={(e) => {
                        e.currentTarget.style.transform = 'translateY(-5px)';
                      }}
                      onMouseOut={(e) => {
                        e.currentTarget.style.transform = 'translateY(0)';
                      }}
                  >
                    <div style={styles.invoiceHeader}>
                <span style={styles.invoiceId}>
                  ID: {invoice.uuid ? invoice.uuid.substring(0, 8) : 'N/A'}
                </span>
                      <span style={styles.invoiceDate}>
                  {formatDate(invoice.invoiceDate)}
                </span>
                    </div>

                    <div style={styles.invoiceDetails}>
                      <div style={styles.detailRow}>
                        <span style={styles.label}>Müşteri:</span>
                        <span style={styles.value}>
                    {invoice.customer ?
                        `${invoice.customer.firstName} ${invoice.customer.lastName}` : 'N/A'}
                  </span>
                      </div>
                      <div style={styles.detailRow}>
                        <span style={styles.label}>Tedarikçi:</span>
                        <span style={styles.value}>
                    {invoice.supplier ? invoice.supplier.name : 'N/A'}
                  </span>
                      </div>
                      <div style={styles.detailRow}>
                        <span style={styles.label}>Ürün:</span>
                        <span style={styles.value}>
                    {invoice.invoiceItem ? invoice.invoiceItem.productName : 'N/A'}
                  </span>
                      </div>
                      <div style={styles.detailRow}>
                        <span style={styles.label}>Not:</span>
                        <span style={styles.value}>
                    {invoice.note || 'Yok'}
                  </span>
                      </div>
                    </div>

                    {invoice.pdfUrl && (
                        <button
                            style={styles.pdfButton}
                            onClick={() => window.open(invoice.pdfUrl, '_blank')}
                            onMouseOver={(e) => {
                              e.target.style.backgroundColor = '#e91e63';
                            }}
                            onMouseOut={(e) => {
                              e.target.style.backgroundColor = '#f093fb';
                            }}
                        >
                          PDF İndir
                        </button>
                    )}
                  </div>
              ))}
            </div>
        )}
      </div>
  );
};

// Automatic Calculation Page
const AutomaticCalculationPage = ({ setCurrentPage }) => {
  const [calculation, setCalculation] = useState({
    unitPrice: '',
    quantity: '1',
    taxRate: '18',
    subtotal: 0,
    taxAmount: 0,
    total: 0
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    const newCalculation = { ...calculation, [name]: value };

    // Automatic calculation
    if (newCalculation.unitPrice && newCalculation.quantity && newCalculation.taxRate) {
      const unitPrice = parseFloat(newCalculation.unitPrice) || 0;
      const quantity = parseFloat(newCalculation.quantity) || 0;
      const taxRate = parseFloat(newCalculation.taxRate) || 0;

      const subtotal = unitPrice * quantity;
      const taxAmount = (subtotal * taxRate) / 100;
      const total = subtotal + taxAmount;

      newCalculation.subtotal = subtotal;
      newCalculation.taxAmount = taxAmount;
      newCalculation.total = total;
    }

    setCalculation(newCalculation);
  };

  const clearCalculation = () => {
    setCalculation({
      unitPrice: '',
      quantity: '1',
      taxRate: '18',
      subtotal: 0,
      taxAmount: 0,
      total: 0
    });
  };

  const styles = {
    container: {
      maxWidth: '800px',
      margin: '0 auto',
      padding: '20px',
      fontFamily: '-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif',
      backgroundColor: '#f8f9fa',
      minHeight: '100vh'
    },
    backButton: {
      padding: '10px 20px',
      backgroundColor: '#6c757d',
      color: '#fff',
      border: 'none',
      borderRadius: '8px',
      cursor: 'pointer',
      marginBottom: '20px',
      fontSize: '14px',
      fontWeight: '600'
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
      background: 'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)',
      WebkitBackgroundClip: 'text',
      WebkitTextFillColor: 'transparent'
    },
    subtitle: {
      color: '#6c757d',
      fontSize: '16px',
      margin: '10px 0 0 0'
    },
    calculatorCard: {
      backgroundColor: '#fff',
      borderRadius: '12px',
      padding: '30px',
      boxShadow: '0 4px 20px rgba(0,0,0,0.1)',
      marginBottom: '20px'
    },
    inputsGrid: {
      display: 'grid',
      gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))',
      gap: '20px',
      marginBottom: '30px'
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
      padding: '12px 16px',
      fontSize: '16px',
      border: '2px solid #e9ecef',
      borderRadius: '8px',
      outline: 'none',
      transition: 'all 0.3s ease'
    },
    resultsCard: {
      backgroundColor: '#f8f9fa',
      borderRadius: '12px',
      padding: '20px',
      marginTop: '20px'
    },
    resultsTitle: {
      fontSize: '18px',
      fontWeight: '600',
      color: '#2c3e50',
      marginBottom: '15px',
      textAlign: 'center'
    },
    resultRow: {
      display: 'flex',
      justifyContent: 'space-between',
      padding: '10px 0',
      borderBottom: '1px solid #dee2e6'
    },
    resultLabel: {
      fontWeight: '500',
      color: '#495057'
    },
    resultValue: {
      fontWeight: '600',
      color: '#2c3e50'
    },
    totalRow: {
      display: 'flex',
      justifyContent: 'space-between',
      padding: '15px 0',
      marginTop: '10px',
      borderTop: '2px solid #4facfe',
      fontSize: '18px',
      fontWeight: '700',
      color: '#2c3e50'
    },
    buttonGroup: {
      display: 'flex',
      gap: '15px',
      marginTop: '20px'
    },
    clearButton: {
      flex: 1,
      padding: '12px',
      backgroundColor: '#6c757d',
      color: '#fff',
      border: 'none',
      borderRadius: '8px',
      cursor: 'pointer',
      fontWeight: '600',
      transition: 'background-color 0.3s ease'
    },
    infoCard: {
      backgroundColor: '#e3f2fd',
      border: '1px solid #bbdefb',
      borderRadius: '8px',
      padding: '15px',
      marginTop: '20px'
    },
    infoTitle: {
      fontSize: '16px',
      fontWeight: '600',
      color: '#1565c0',
      marginBottom: '8px'
    },
    infoText: {
      fontSize: '14px',
      color: '#1976d2',
      lineHeight: '1.5'
    }
  };

  return (
      <div style={styles.container}>
        <button
            style={styles.backButton}
            onClick={() => setCurrentPage('home')}
        >
          ← Ana Sayfaya Dön
        </button>

        <div style={styles.header}>
          <h1 style={styles.title}>Automatic Calculation</h1>
          <p style={styles.subtitle}>
            KDV, toplam tutar ve ara toplamları otomatik hesaplama sistemi
          </p>
        </div>

        <div style={styles.calculatorCard}>
          <div style={styles.inputsGrid}>
            <div style={styles.fieldGroup}>
              <label style={styles.label}>Birim Fiyat (₺)</label>
              <input
                  type="number"
                  name="unitPrice"
                  value={calculation.unitPrice}
                  onChange={handleInputChange}
                  style={styles.input}
                  placeholder="0.00"
                  step="0.01"
                  min="0"
              />
            </div>

            <div style={styles.fieldGroup}>
              <label style={styles.label}>Miktar</label>
              <input
                  type="number"
                  name="quantity"
                  value={calculation.quantity}
                  onChange={handleInputChange}
                  style={styles.input}
                  placeholder="1"
                  min="1"
              />
            </div>

            <div style={styles.fieldGroup}>
              <label style={styles.label}>KDV Oranı (%)</label>
              <input
                  type="number"
                  name="taxRate"
                  value={calculation.taxRate}
                  onChange={handleInputChange}
                  style={styles.input}
                  placeholder="18"
                  min="0"
                  max="100"
              />
            </div>
          </div>

          <div style={styles.resultsCard}>
            <h3 style={styles.resultsTitle}>Hesaplama Sonuçları</h3>

            <div style={styles.resultRow}>
              <span style={styles.resultLabel}>Ara Toplam:</span>
              <span style={styles.resultValue}>₺{calculation.subtotal.toFixed(2)}</span>
            </div>

            <div style={styles.resultRow}>
              <span style={styles.resultLabel}>KDV Tutarı ({calculation.taxRate}%):</span>
              <span style={styles.resultValue}>₺{calculation.taxAmount.toFixed(2)}</span>
            </div>

            <div style={styles.totalRow}>
              <span>Genel Toplam:</span>
              <span>₺{calculation.total.toFixed(2)}</span>
            </div>
          </div>

          <div style={styles.buttonGroup}>
            <button
                style={styles.clearButton}
                onClick={clearCalculation}
                onMouseOver={(e) => {
                  e.target.style.backgroundColor = '#5a6268';
                }}
                onMouseOut={(e) => {
                  e.target.style.backgroundColor = '#6c757d';
                }}
            >
              Temizle
            </button>
          </div>

          <div style={styles.infoCard}>
            <h4 style={styles.infoTitle}>Nasıl Kullanılır?</h4>
            <p style={styles.infoText}>
              • Birim fiyat, miktar ve KDV oranını girin<br/>
              • Sistem otomatik olarak ara toplam, KDV tutarı ve genel toplamı hesaplayacak<br/>
              • KDV, toplam tutar, ara toplam gibi alanları kullanıcı manuel girmeden, sistem hesaplaması destekte<br/>
              • React formunda dinamik hesaplama desteği eklendi
            </p>
          </div>
        </div>
      </div>
  );
};

// Main App Component
function App() {
  const [currentPage, setCurrentPage] = useState('home');

  return (
      <Router currentPage={currentPage} setCurrentPage={setCurrentPage}>
        <Route path="home" component={HomePage} />
        <Route path="generate-invoice" component={GenerateInvoicePage} />
        <Route path="show-invoices" component={ShowInvoicesPage} />
        <Route path="automatic-calculation" component={AutomaticCalculationPage} />
      </Router>
  );
}

export default App;