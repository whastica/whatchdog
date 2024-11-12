//endpoints.ts

export const getReports = async () => {
  const response = await fetch("http://localhost:8080/api/reports", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });

  if (!response.ok) {
    throw new Error("Error al obtener los reportes");
  }

  const data = await response.json();
  return data; // Retorna el array de reportes
};

export const deleteReport = async (idReport: string) => {
  const response = await fetch(
    `http://localhost:8080/api/reports/${idReport}`,
    {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    }
  );

  if (!response.ok) {
    throw new Error("Error al eliminar el reporte");
  }

  return response; // Devuelve la respuesta para confirmar la eliminación
};

export const getIssues = async () => {
  const response = await fetch("http://localhost:8081/api/v1/issues", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });

  if (!response.ok) {
    throw new Error("Error al obtener los issues");
  }

  const data = await response.json();
  return data; // Retorna el array de issues
};

export const createIssue = async (issueData: {
  id: string;
  category: string;
  priority: string;
  statusIssue: string;
  resolutionTeam: string;
  admincId: string;
}) => {
  const response = await fetch("http://localhost:8081/api/v1/issues", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(issueData),
  });

  if (!response.ok) {
    throw new Error("Error al crear el issue");
  }

  const data = await response.json();
  return data;
};
