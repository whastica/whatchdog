import React from "react";

interface Comment {
  id: number;
  author: string;
  content: string;
}

interface CommentsSectionProps {
  comments: Comment[];
}

const CommentsSection: React.FC<CommentsSectionProps> = ({ comments }) => {
  return (
    <div className="bg-white shadow-md rounded-md p-6">
      <h2 className="text-xl font-bold mb-4">Comentarios</h2>

      {comments.length > 0 ? (
        comments.map((comment) => (
          <div key={comment.id} className="mb-4">
            <p className="font-semibold text-gray-700">{comment.author}</p>
            <p className="text-gray-600">{comment.content}</p>
            <hr className="my-2" />
          </div>
        ))
      ) : (
        <p className="text-gray-600">No hay comentarios disponibles.</p>
      )}
    </div>
  );
};

export default CommentsSection;
