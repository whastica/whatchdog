import React from "react";

interface ReportDetailsProps {
  report: {
    fotoUrl: string;
    createDate: string;
    description: string;
    numLikes: number;
    numDislikes: number;
    status: string;
  };
}

// Función para formatear la fecha
const formatDate = (dateString: string) => {
  const date = new Date(dateString);
  return date.toLocaleDateString("es-ES", {
    year: "numeric",
    month: "long",
    day: "numeric",
  });
};

const ReportDetails: React.FC<ReportDetailsProps> = ({ report }) => {
  return (
    <div className="bg-white shadow-md rounded-md p-6">
      <p className="text-gray-600 font-semibold mb-2">
        URL de foto:{" "}
        {report.fotoUrl ? (
          <a
            href={report.fotoUrl}
            target="_blank"
            rel="noopener noreferrer"
            className="text-blue-500"
          >
            {report.fotoUrl}
          </a>
        ) : (
          <span className="text-gray-400">No disponible</span>
        )}
      </p>

      <p className="text-gray-600 font-semibold mb-2">
        Fecha de creación:{" "}
        {report.createDate
          ? formatDate(report.createDate)
          : "Fecha no disponible"}
      </p>

      <p className="text-gray-600 font-semibold mb-2">
        Descripción: {report.description ?? "Descripción no disponible"}
      </p>

      <div className="flex justify-between items-center mb-2">
        <div className="flex flex-col items-center">
          <p className="text-gray-600 font-semibold mt-1">
            Likes: {report.numLikes ?? 0}
          </p>
        </div>
        <div className="flex flex-col items-center">
          <p className="text-gray-600 font-semibold mt-1">
            Dislikes: {report.numDislikes ?? 0}
          </p>
        </div>
      </div>

      <p className="text-gray-600 font-semibold mb-2">
        Estado: {report.status ?? "Estado no disponible"}
      </p>
    </div>
  );
};

export default ReportDetails;
