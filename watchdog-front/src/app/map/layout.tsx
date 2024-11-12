import React from "react";
import Sidebar from "@/components/sidebar";

const ReportLayout = ({ children }: { children: React.ReactNode }) => {
  return (
    <div className="flex">
      <Sidebar />
      <main className="flex-1 min-h-screen">{children}</main>
    </div>
  );
};

export default ReportLayout;
