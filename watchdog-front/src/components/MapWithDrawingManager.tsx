import React, { useEffect, useState } from "react";
import { Map, InfoWindow, useMap } from "@vis.gl/react-google-maps";

interface MapWithDrawingManagerProps {
  onMarkerPositionChange: (position: { lat: number; lng: number }) => void;
}

const MapWithDrawingManager: React.FC<MapWithDrawingManagerProps> = ({
  onMarkerPositionChange,
}) => {
  const [marker, setMarker] = useState<google.maps.Marker | null>(null);
  const [showInfoWindow, setShowInfoWindow] = useState(false);
  const [drawingManager, setDrawingManager] =
    useState<google.maps.drawing.DrawingManager | null>(null);
  const [mapCenter, setMapCenter] = useState<{
    lat: number;
    lng: number;
  } | null>(null);
  const map = useMap();

  // Solicitar la ubicación del usuario al montar el componente
  useEffect(() => {
    if ("geolocation" in navigator) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const { latitude, longitude } = position.coords;
          setMapCenter({ lat: latitude, lng: longitude });
        },
        (error) => {
          console.error("Error al obtener la ubicación:", error);
          setMapCenter({ lat: 2.4448, lng: -76.6147 }); // Popayán, Colombia
        }
      );
    } else {
      console.error("La geolocalización no está disponible en este navegador.");
      setMapCenter({ lat: 2.4448, lng: -76.6147 }); // Popayán, Colombia
    }
  }, []);

  // Mover el mapa a la ubicación del usuario una vez obtenida
  useEffect(() => {
    if (map && mapCenter) {
      map.panTo(mapCenter); // Mover el mapa a la posición obtenida
      map.setZoom(18); // Ajustar el zoom para una vista más cercana
    }
  }, [map, mapCenter]);

  useEffect(() => {
    if (!map || !mapCenter) return;

    const newDrawingManager = new google.maps.drawing.DrawingManager({
      drawingMode: google.maps.drawing.OverlayType.MARKER,
      drawingControl: false,
      markerOptions: {
        draggable: true,
      },
    });

    newDrawingManager.setMap(map);
    setDrawingManager(newDrawingManager);

    const handleOverlayComplete = (
      event: google.maps.drawing.OverlayCompleteEvent
    ) => {
      if (marker) {
        marker.setMap(null);
      }

      const newMarker = event.overlay as google.maps.Marker;
      setMarker(newMarker);
      updatePosition(newMarker);

      newMarker.addListener("dragend", () => updatePosition(newMarker));
      newMarker.addListener("click", () => setShowInfoWindow(true));

      newDrawingManager.setDrawingMode(null);
    };

    google.maps.event.addListener(
      newDrawingManager,
      "overlaycomplete",
      handleOverlayComplete
    );

    return () => {
      google.maps.event.clearInstanceListeners(newDrawingManager);
      newDrawingManager.setMap(null);
    };
  }, [map, mapCenter]);

  const updatePosition = (newMarker: google.maps.Marker) => {
    const position = newMarker.getPosition();
    if (position) {
      const lat = position.lat();
      const lng = position.lng();
      onMarkerPositionChange({ lat, lng });
    }
  };

  const removeMarker = () => {
    if (marker) {
      marker.setMap(null);
      setMarker(null);
      setShowInfoWindow(false);
      drawingManager?.setDrawingMode(google.maps.drawing.OverlayType.MARKER);
    }
  };

  return (
    <div className="relative w-full h-full">
      {mapCenter ? (
        <Map
          defaultZoom={12} // Zoom inicial
          defaultCenter={{ lat: 2.4448, lng: -76.6147 }} // Centro inicial en Popayán, mientras se obtiene la ubicación
          className="w-full h-full"
        />
      ) : (
        <div className="flex items-center justify-center w-full h-full">
          <p>Obteniendo ubicación...</p>
        </div>
      )}

      {marker && showInfoWindow && (
        <InfoWindow
          anchor={marker}
          onCloseClick={() => setShowInfoWindow(false)}
        >
          <div>
            <button
              onClick={removeMarker}
              className="px-4 py-2 bg-red-500 text-white rounded"
            >
              Eliminar marcador
            </button>
          </div>
        </InfoWindow>
      )}
    </div>
  );
};

export default MapWithDrawingManager;
