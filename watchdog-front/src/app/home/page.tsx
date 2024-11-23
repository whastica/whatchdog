"use client";

import React, { useEffect, useState } from "react";
import ReportCard from "@/components/ReportCard";
import { getReports } from "@/services/endpoint";

interface Report {
  idReport: string;
  description: string;
  fotoUrl: string;
  createDate: string;
  numLikes: number;
  numDislikes: number;
  status: string;
}

const HomePage = () => {
  const [reports, setReports] = useState<Report[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchReports = async () => {
      try {
        const data = await getReports();
        setReports(data);
      } catch (error) {
        console.error("Error al cargar los reportes:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchReports();
  }, []);

  if (loading) return <p>Cargando reportes...</p>;

  return (
    <div className="grid grid-cols-1 lg:grid-cols-3 xl:grid-cols-5 gap-4 p-4">
      {reports.map((report) => (
        <ReportCard
          key={report.idReport}
          reportId={report.idReport}
          isAdmin={false}
          description={report.description}
          fotoUrl={report.fotoUrl}
          createDate={report.createDate}
          numLikes={report.numLikes}
          numDislikes={report.numDislikes}
          status={report.status}
        />
      ))}
    </div>
  );
};

export default HomePage;
