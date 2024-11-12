"use client";

import React, { useEffect, useState } from "react";
import ReportDetails from "@/components/ReportDetails";
import CommentsSection from "@/components/CommentsSection";

interface ReportPageProps {
  params: {
    report_id: string;
  };
}

const ReportPage: React.FC<ReportPageProps> = ({ params }) => {
  const { report_id } = params;
  const [reportData, setReportData] = useState<any>(null);

  useEffect(() => {
    const fetchReportData = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/api/reports/${report_id}`
        );
        if (response.ok) {
          const data = await response.json();
          setReportData(data);
        } else {
          console.error("Error fetching report data");
        }
      } catch (error) {
        console.error("Error:", error);
      }
    };

    fetchReportData();
  }, [report_id]);

  return (
    <div className="min-h-screen p-8">
      <h1 className="text-3xl font-bold mb-6 text-center">
        Detalles del Reporte
      </h1>

      <div className="space-y-8">
        {reportData ? (
          <>
            <ReportDetails report={reportData} />
            <CommentsSection comments={reportData.comments} />
          </>
        ) : (
          <p className="text-center">Cargando datos...</p>
        )}
      </div>
    </div>
  );
};

export default ReportPage;
