"use client";

import React, { useState } from "react";

interface IssueModalProps {
  isOpen: boolean;
  onClose: () => void;
  onCreate: (data: {
    category: string;
    status: string;
    priority: string;
    resolutionTeam: string;
  }) => void;
}

const IssueModal: React.FC<IssueModalProps> = ({
  isOpen,
  onClose,
  onCreate,
}) => {
  const [category, setCategory] = useState("");
  const [status, setStatus] = useState("");
  const [priority, setPriority] = useState("");
  const [resolutionTeam, setResolutionTeam] = useState("");

  const handleCreate = () => {
    onCreate({ category, status, priority, resolutionTeam });
    onClose();
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50">
      <div className="bg-white p-6 rounded-md w-80 shadow-lg">
        <h2 className="text-xl font-bold mb-4">Crear Problema</h2>

        {/* Campo de Categoría */}
        <label className="block mb-2">
          <span className="text-gray-700">Categoría</span>
          <select
            value={category}
            onChange={(e) => setCategory(e.target.value)}
            className="w-full border border-gray-300 p-2 rounded mt-1"
          >
            <option value="">Seleccione una categoría</option>
            <option value="INFRAESTRUCTURA_VIAL">Infraestructura Vial</option>
            <option value="ILUMINACION_PUBLICA">Iluminación Pública</option>
            <option value="GESTION_RESIDUOS">Gestión de Residuos</option>
            <option value="ESPACIOS_VERDES">Espacios Verdes</option>
            <option value="TRANSPORTE_PUBLICO">Transporte Público</option>
            <option value="AGUA_Y_DRENAJE">Agua y Drenaje</option>
            <option value="SEGURIDAD_CIUDADANA">Seguridad Ciudadana</option>
            <option value="CONTAMINACION">Contaminación</option>
            <option value="SERVICIOS_PUBLICOS">Servicios Públicos</option>
            <option value="EDIFICACIONES_Y_OBRAS">Edificaciones y Obras</option>
            <option value="MOVILIDAD_Y_ACCESIBILIDAD">
              Movilidad y Accesibilidad
            </option>
            <option value="ANIMALES">Animales</option>
            <option value="INSEGURIDAD_VIAL">Inseguridad Vial</option>
            <option value="MOBILIARIO_URBANO">Mobiliario Urbano</option>
          </select>
        </label>

        {/* Campo de Estado */}
        <label className="block mb-2">
          <span className="text-gray-700">Estado</span>
          <select
            value={status}
            onChange={(e) => setStatus(e.target.value)}
            className="w-full border border-gray-300 p-2 rounded mt-1"
          >
            <option value="">Seleccionar estado</option>
            <option value="ASIGNADO">Asignado</option>
            <option value="EN_PROCESO">En Proceso</option>
            <option value="RESUELTO">Resuelto</option>
          </select>
        </label>

        {/* Campo de Prioridad */}
        <label className="block mb-2">
          <span className="text-gray-700">Prioridad</span>
          <select
            value={priority}
            onChange={(e) => setPriority(e.target.value)}
            className="w-full border border-gray-300 p-2 rounded mt-1"
          >
            <option value="">Seleccionar prioridad</option>
            <option value="ALTA">Alta</option>
            <option value="MEDIA">Media</option>
            <option value="BAJA">Baja</option>
          </select>
        </label>

        {/* Campo de Equipo de Resolución */}
        <label className="block mb-4">
          <span className="text-gray-700">Equipo de Resolución</span>
          <select
            value={resolutionTeam}
            onChange={(e) => setResolutionTeam(e.target.value)}
            className="w-full border border-gray-300 p-2 rounded mt-1"
          >
            <option value="">Seleccionar equipo</option>
            <option value="INFRAESTRUCTURA_Y_OBRAS_PUBLICAS">
              Infraestructura y obras publicas
            </option>
            <option value="SERVICIOS_PUBLICOS">Servicios publicos</option>
            <option value="MEDIO_AMBIENTE">Medio ambiente</option>
            <option value="SEGURIDAD_CIUDADANA">Seguridad ciudadana</option>
            <option value="MOVILIDAD_Y_TRANSPORTE">
              Movilidad y transporte
            </option>
          </select>
        </label>

        {/* Botones de acción */}
        <div className="flex justify-end space-x-4">
          <button
            onClick={onClose}
            className="px-4 py-2 bg-gray-500 text-white rounded hover:bg-gray-600"
          >
            Cancelar
          </button>
          <button
            onClick={handleCreate}
            className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
          >
            Crear
          </button>
        </div>
      </div>
    </div>
  );
};

export default IssueModal;
