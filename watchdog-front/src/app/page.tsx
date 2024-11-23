import Link from "next/link";
import WatchDogLogo from "@/components/icons/WatchDogLogo";

export default function LandingPage() {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen">
      <WatchDogLogo className="w-40 h-40 mb-6 text-black" />
      <h1 className="text-6xl font-bold mb-8 text-black">
        Watch<span className="text-blue-600">Dog</span>
      </h1>
      <Link href="/home">
        <button className="px-6 py-3 bg-blue-600 text-white rounded-lg text-lg font-medium hover:bg-blue-700">
          Ingresar
        </button>
      </Link>
    </div>
  );
}
