import React from "react";
import { APIProvider } from "@vis.gl/react-google-maps";
import MapWithDrawingManager from "./MapWithDrawingManager";

const API_KEY = "AIzaSyCFFU5iBEjdmW7N5Ey0OQB27Dy-foSx_bA";

interface MapWithMarkerProps {
  onMarkerPositionChange: (position: { lat: number; lng: number }) => void;
}

const MapWithMarker: React.FC<MapWithMarkerProps> = ({
  onMarkerPositionChange,
}) => {
  return (
    <APIProvider apiKey={API_KEY} libraries={["drawing"]}>
      <MapWithDrawingManager onMarkerPositionChange={onMarkerPositionChange} />
    </APIProvider>
  );
};

export default MapWithMarker;
