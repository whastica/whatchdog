import React from "react";
import WorkCaseIcon from "./icons/WorkCaseIcon";
import { useRouter } from "next/navigation";

interface IssueCardProps {
  issue: {
    id: string;
    category: string;
    priority: string;
    statusIssue: string;
    resolutionTeam: string;
    reportIds: string[];
  };
}

const IssueCard: React.FC<IssueCardProps> = ({ issue }) => {
  return (
    <div className="bg-white shadow-md rounded-md p-4 h-auto flex flex-col justify-between">
      {/* Ícono del Issue */}
      <div className="flex justify-center items-center mb-4">
        <div className="bg-gray-200 w-full h-32 flex justify-center items-center rounded-md">
          <WorkCaseIcon className="w-20 h-20 text-gray-600" />
        </div>
      </div>

      {/* Información del Issue */}
      <div>
        {/* Categoría */}
        <p className="text-gray-500 font-semibold mb-1">Categoría:</p>
        <p className="text-gray-700 truncate mb-2">{issue.category ?? "N/A"}</p>

        {/* Estado */}
        <p className="text-gray-500 font-semibold mb-1">Estado:</p>
        <p className="text-gray-700 truncate mb-2">
          {issue.statusIssue ?? "N/A"}
        </p>

        {/* Prioridad */}
        <p className="text-gray-500 font-semibold mb-1">Prioridad:</p>
        <p className="text-gray-700 truncate mb-2">{issue.priority ?? "N/A"}</p>

        {/* Número de reportes */}
        <p className="text-gray-500 font-semibold mb-1">Número de Reportes:</p>
        <p className="text-gray-700 mb-2">{issue.reportIds.length ?? 0}</p>

        {/* Equipo de resolución */}
        <p className="text-gray-500 font-semibold mb-1">
          Equipo de Resolución:
        </p>
        <p className="text-gray-700 truncate">
          {issue.resolutionTeam ?? "N/A"}
        </p>
      </div>
    </div>
  );
};

export default IssueCard;
