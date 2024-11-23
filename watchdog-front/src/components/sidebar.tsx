import React from "react";
import Link from "next/link";
import WatchDogLogo from "@/components/icons/WatchDogLogo";

const Sidebar = () => {
  return (
    <aside className="w-64 min-h-screen bg-[#000B27] text-white p-6 flex flex-col items-center">
      {/* Logo en la parte superior del sidebar */}
      <div className="flex flex-col items-center mb-8 mt-8">
        <WatchDogLogo className="w-20 h-20 mb-2" />
        <h1 className="text-2xl font-bold">WatchDog</h1>
      </div>

      {/* Navegación */}
      <nav className="w-full">
        <ul className="flex flex-col">
          <li className="mb-4 w-full">
            <Link
              href="/home"
              className="text-lg font-semibold px-4 py-2 w-full block text-left hover:bg-gray-700 rounded"
            >
              Inicio (user)
            </Link>
          </li>
          <li className="mb-4 w-full">
            <Link
              href="/map"
              className="text-lg font-semibold px-4 py-2 w-full block text-left hover:bg-gray-700 rounded"
            >
              Crear reporte
            </Link>
          </li>
          <li className="mb-4 w-full">
            <Link
              href="/home"
              className="text-lg font-semibold px-4 py-2 w-full block text-left hover:bg-gray-700 rounded"
            >
              Mis reportes
            </Link>
          </li>
          <li className="mb-4 w-full">
            <Link
              href="/admin"
              className="text-lg font-semibold px-4 py-2 w-full block text-left hover:bg-gray-700 rounded"
            >
              Inicio (admin)
            </Link>
          </li>
          <li className="mb-4 w-full">
            <Link
              href="/issue"
              className="text-lg font-semibold px-4 py-2 w-full block text-left hover:bg-gray-700 rounded"
            >
              Problemas (admin)
            </Link>
          </li>
        </ul>
      </nav>
    </aside>
  );
};

export default Sidebar;
