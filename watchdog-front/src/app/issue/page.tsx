"use client";

import React, { useState, useEffect } from "react";
import PlusIcon from "@/components/icons/PlusIcon";
import IssueCard from "@/components/IssueCard";
import IssueModal from "@/components/IssueModal";
import { getIssues, createIssue } from "@/services/endpoint";
import { useRouter } from "next/navigation";

interface Issue {
  id: string;
  category: string;
  priority: string;
  statusIssue: string;
  resolutionTeam: string;
  reportIds: string[];
}

const IssuePage: React.FC = () => {
  const router = useRouter();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [issues, setIssues] = useState<Issue[]>([]);

  const handleCreateIssue = () => {
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
  };

  const handleIssueCreation = async (data: {
    category: string;
    status: string;
    priority: string;
    resolutionTeam: string;
  }) => {
    const newIssue = {
      id: `iss${Math.floor(Math.random() * 1000)}`,
      admincId: "ad1",
      category: data.category,
      priority: data.priority,
      statusIssue: data.status,
      resolutionTeam: data.resolutionTeam,
      reportIds: [],
    };

    try {
      await createIssue(newIssue);
      fetchIssues(); // Recargar los issues después de crear uno nuevo
      alert("Issue creado exitosamente");
    } catch (error) {
      console.error("Error al crear el issue:", error);
      alert("Error al crear el issue. Intente nuevamente.");
    } finally {
      setIsModalOpen(false);
    }
  };

  const fetchIssues = async () => {
    try {
      const data = await getIssues();
      setIssues(data);
    } catch (error) {
      console.error("Error al cargar los issues:", error);
    }
  };

  useEffect(() => {
    fetchIssues();
  }, []);

  return (
    <div className="min-h-screen p-8">
      <h1 className="text-3xl font-bold text-center mb-8">
        Gestión de Problemas
      </h1>

      <div className="grid grid-cols-1 lg:grid-cols-4 xl:grid-cols-6 gap-6">
        <button
          onClick={handleCreateIssue}
          className="w-full flex flex-col items-center justify-center border-2 border-dashed border-gray-400 rounded-lg hover:border-blue-500 hover:bg-gray-200 focus:outline-none h-auto p-6"
        >
          <PlusIcon className="w-8 h-8 text-gray-600 mb-2" />
          <span className="text-lg font-semibold text-gray-600">
            Crear Problema
          </span>
        </button>

        {issues.map((issue) => (
          <IssueCard key={issue.id} issue={issue} />
        ))}
      </div>

      <IssueModal
        isOpen={isModalOpen}
        onClose={handleCloseModal}
        onCreate={handleIssueCreation}
      />
    </div>
  );
};

export default IssuePage;
