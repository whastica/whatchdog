"use client";

import React, { useState } from "react";
import { useRouter } from "next/navigation";
import LikeIcon from "./icons/LikeIcon";
import DislikeIcon from "./icons/DislikeIcon";
import MoreVerticalIcon from "./icons/MoreVerticalIcon";
import ReportIcon from "./icons/ReportIcon";
import { deleteReport } from "@/services/endpoint"; // Importa la función de eliminación

interface ReportCardProps {
  isAdmin?: boolean;
  reportId: string;
  description: string;
  fotoUrl: string;
  createDate: string;
  numLikes: number;
  numDislikes: number;
  status: string;
  numComments?: number;
}

const formatDate = (dateString: string) => {
  const date = new Date(dateString);
  return date.toLocaleDateString("es-ES", {
    year: "numeric",
    month: "long",
    day: "numeric",
  });
};

const ReportCard: React.FC<ReportCardProps> = ({
  isAdmin = false,
  reportId,
  description,
  fotoUrl,
  createDate,
  numLikes,
  numDislikes,
  status,
  numComments = 0,
}) => {
  const router = useRouter();
  const [showOptions, setShowOptions] = useState(false);

  const toggleOptions = () => {
    setShowOptions(!showOptions);
  };

  const handleOpenReport = () => {
    router.push(`/report/${reportId}`);
  };

  const handleDeleteReport = async () => {
    const confirmed = confirm(
      "¿Está seguro de que desea eliminar este reporte?"
    );
    if (!confirmed) return;

    try {
      await deleteReport(reportId);
      alert("Reporte eliminado exitosamente");
      // Redirecciona o refresca la página para reflejar el cambio
      router.refresh();
    } catch (error) {
      console.error("Error al eliminar el reporte:", error);
      alert("No se pudo eliminar el reporte. Intente nuevamente.");
    }
  };

  return (
    <div className="bg-white shadow-md rounded-md p-4 mb-2 relative">
      {isAdmin && (
        <div className="absolute top-2 right-2">
          <button onClick={toggleOptions} className="focus:outline-none m-2">
            <MoreVerticalIcon className="w-4 h-4 text-gray-600 hover:text-gray-800" />
          </button>
          {showOptions && (
            <div className="absolute right-0 mt-2 w-24 bg-white shadow-lg rounded-md z-10">
              <ul className="text-sm text-gray-700">
                <li className="px-4 py-2 hover:bg-gray-100 cursor-pointer">
                  Modificar
                </li>
                <li
                  onClick={handleDeleteReport}
                  className="px-4 py-2 hover:bg-red-100 text-red-500 cursor-pointer"
                >
                  Eliminar
                </li>
              </ul>
            </div>
          )}
        </div>
      )}

      {/* Placeholder del ícono de reporte */}
      <div className="flex justify-center items-center mb-4">
        <div className="bg-gray-200 w-3/4 h-32 flex justify-center items-center rounded-md">
          <ReportIcon className="w-20 h-20 text-gray-600" />
        </div>
      </div>

      <p className="flex text-gray-600 font-semibold mb-2 items-center mt-4">
        Url de foto: {fotoUrl || "No disponible"}
      </p>

      <div className={`${isAdmin ? "mt-2" : "mt-4"}`}>
        <p className="flex text-gray-600 font-semibold mb-2 items-center">
          Fecha de creación: {formatDate(createDate)}
        </p>
        <p className="text-gray-600 font-semibold mb-2">
          Descripción: {description}
        </p>

        <div className="flex justify-between items-center mb-2">
          <div className="flex flex-col items-center">
            <div className="w-10 h-10 flex items-center justify-center rounded-full bg-gray-200 hover:bg-gray-300 cursor-pointer">
              <LikeIcon className="w-6 h-6 text-gray-600 hover:text-green-600" />
            </div>
            <p className="text-gray-600 font-semibold mt-1">
              Likes: {numLikes}
            </p>
          </div>
          <div className="flex flex-col items-center">
            <div className="w-10 h-10 flex items-center justify-center rounded-full bg-gray-200 hover:bg-gray-300 cursor-pointer">
              <DislikeIcon className="w-6 h-6 text-gray-600 hover:text-red-600" />
            </div>
            <p className="text-gray-600 font-semibold mt-1">
              Dislikes: {numDislikes}
            </p>
          </div>
        </div>

        <p className="flex text-gray-600 font-semibold mb-2 items-center">
          Estado: {status}
        </p>

        <p className="text-gray-600 font-semibold">
          Comentarios: {numComments}
        </p>
      </div>

      <div className="mt-4 flex justify-center">
        <button
          onClick={handleOpenReport}
          className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 w-full"
        >
          Ver más
        </button>
      </div>
    </div>
  );
};

export default ReportCard;
