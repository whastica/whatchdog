"use client";

import React, { useState } from "react";
import MapWithMarker from "../../components/MapWithMarker";
import { useRouter } from "next/navigation";

const ReportPage = () => {
  const router = useRouter();
  const [coordinates, setCoordinates] = useState<{
    lat: number;
    lng: number;
  } | null>(null);
  const [imageUrl, setImageUrl] = useState<string>("");
  const [description, setDescription] = useState<string>("");
  const [categoryIssue, setCategoryIssue] = useState<string>("");

  const handleMarkerPosition = (position: { lat: number; lng: number }) => {
    setCoordinates(position);
  };

  const handleCreateReport = async () => {
    if (!coordinates || !categoryIssue || !description || !imageUrl) {
      alert("Por favor, complete todos los campos.");
      return;
    }

    const reportData = {
      idReport: Math.floor(Math.random() * 1000).toString(),
      description,
      idCitizen: "ci1",
      idAdminC: "ad1",
      status: "EN_LISTA",
      categoryIssue,
      coordinates: {
        latitude: coordinates.lat,
        longitude: coordinates.lng,
      },
      createDate: new Date().toISOString(),
      updateDate: new Date().toISOString(),
      fotoUrl: imageUrl,
      numLikes: Math.floor(Math.random() * 10).toString(),
      numDislikes: Math.floor(Math.random() * 10).toString(),
    };

    try {
      const response = await fetch("http://localhost:8080/api/reports", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(reportData),
      });

      if (response.ok) {
        alert("Reporte creado exitosamente");
        router.push("/home");
      } else {
        const errorText = await response.text();
        console.error("Error al crear el reporte:", errorText);
        alert("Error al crear el reporte.");
      }
    } catch (error) {
      console.error("Error en la creación del reporte:", error);
      alert("Ocurrió un error al crear el reporte.");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center p-4">
      <div className="w-full max-w-7xl bg-white shadow-lg p-6 rounded-md space-y-6 lg:max-w-4xl">
        <h1 className="text-3xl font-bold text-center">
          Crear Reporte de Ubicación
        </h1>

        <label className="block text-gray-700 font-semibold mb-2">
          Seleccione una ubicación en el mapa
        </label>

        <div className="w-full h-[400px] xl:h-[350px] lg:h-[300px] md:h-[300px] sm:h-[250px] border rounded overflow-hidden">
          <MapWithMarker onMarkerPositionChange={handleMarkerPosition} />
        </div>

        {/* {coordinates && (
          <div className="p-2 border rounded bg-gray-100">
            <p>
              <strong>Latitud:</strong> {coordinates.lat}
            </p>
            <p>
              <strong>Longitud:</strong> {coordinates.lng}
            </p>
          </div>
        )} */}

        <form className="space-y-4">
          <div>
            <label className="block text-gray-700 font-semibold">
              Descripción
            </label>
            <textarea
              className="w-full border border-gray-300 p-2 rounded"
              placeholder="Descripción del reporte"
              rows={3}
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            />
          </div>

          <div>
            <label className="block text-gray-700 font-semibold">
              Categoría del Problema
            </label>
            <select
              value={categoryIssue}
              onChange={(e) => setCategoryIssue(e.target.value)}
              className="w-full border border-gray-300 p-2 rounded"
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
              <option value="EDIFICACIONES_Y_OBRAS">
                Edificaciones y Obras
              </option>
              <option value="MOVILIDAD_Y_ACCESIBILIDAD">
                Movilidad y Accesibilidad
              </option>
              <option value="ANIMALES">Animales</option>
              <option value="INSEGURIDAD_VIAL">Inseguridad Vial</option>
              <option value="MOBILIARIO_URBANO">Mobiliario Urbano</option>
            </select>
          </div>

          <div>
            <label className="block text-gray-700 font-semibold">
              URL de la Imagen
            </label>
            <input
              type="text"
              value={imageUrl}
              onChange={(e) => setImageUrl(e.target.value)}
              placeholder="Ingrese la URL de la imagen (PNG, JPG, JPEG)"
              className="w-full border border-gray-300 p-2 rounded"
            />
          </div>

          <div className="flex space-x-4">
            <button
              type="button"
              onClick={() => router.back()}
              className="w-full bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600"
            >
              Regresar
            </button>
            <button
              type="button"
              onClick={handleCreateReport}
              className="w-full bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
            >
              Crear
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ReportPage;
